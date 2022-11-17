package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ActivateCampaignController implements Initializable {

    @FXML
    private Button confirmButton;
    @FXML
    private Label warningLabel;
    @FXML
    private Button goBackButton;
    @FXML
    private TextField availableVaccines;
    @FXML
    private ComboBox<String> availableCampaigns;
    @FXML
    private TextField vaccineName;
    @FXML
    private ListView<String> category;
    @FXML
    private DatePicker startCampaign;
    @FXML
    private ListView<String> clinicsAvailable;

    private final ObservableList<String> campaignList = FXCollections.observableArrayList();
    private final ObservableList<String> categoryList = FXCollections.observableArrayList();
    private final ObservableList<String> clinicList = FXCollections.observableArrayList();
    CampaignDao campaignDB = CampaignDaoImpl.getInstance();
    ClinicDao clinicDB = ClinicDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableVaccines.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                availableVaccines.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });



        for (Campaign cmp:campaignDB.getAllCampaigns()) {
            if (!cmp.isActive()) {
                campaignList.add(cmp.getName());
            }
        }
        for (CategorieCittadini ctg:CategorieCittadini.values()) {
            categoryList.add(CategorieCittadini.toString(ctg));
        }

        for (Clinic cln:clinicDB.getAllClinics()) {
            clinicList.add(cln.getName());
        }

        availableCampaigns.setItems(campaignList);
        category.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        clinicsAvailable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        category.setItems(categoryList);
        clinicsAvailable.setItems(clinicList);

        availableCampaigns.valueProperty().addListener((observable, oldValue, newValue) -> {
            //clinicInfo.setText(campaign.getName());
            if (!campaignDB.getCampaign(availableCampaigns.getSelectionModel().getSelectedItem()).getVaccine().equals("null")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/popUpActivation.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene newScene = new Scene(root, 300, 200);
                PopUpActivationController popUpActivationController = loader.getController();

                Stage currentStage = (Stage) category.getScene().getWindow();
                popUpActivationController.setData(campaignDB.getCampaign(availableCampaigns.getSelectionModel().getSelectedItem()), this);
                Stage popUp = new Stage();
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(currentStage);
                popUp.setTitle("Notifiche");
                popUp.setScene(newScene);
                popUp.show();



            }
        });

    }

    public void activateCampaign(ActionEvent actionEvent) throws IOException {

        if (checkValidity()) {

            Set<CategorieCittadini> ctgSet = new HashSet<>();
            Set<Clinic> clnSet = new HashSet<>();
            Campaign campaign = campaignDB.getCampaign(availableCampaigns.getSelectionModel().getSelectedItem());
            campaign.setStartCampaign(startCampaign.getValue());
            campaign.setAvailability(Integer.parseInt(availableVaccines.getText()));
            campaign.setVaccine(vaccineName.getText());

            for (String ctg : category.getSelectionModel().getSelectedItems()) {
                ctgSet.add(CategorieCittadini.toCategory(ctg));
            }
            campaign.setCategories(ctgSet);
            for (String cln : clinicsAvailable.getSelectionModel().getSelectedItems()) {
                clnSet.add(clinicDB.getClinic(cln));
            }
            campaign.setClinics(clnSet);
            campaignDB.updateCampaign(campaign);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/activateCampaign.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root, 600, 400);
            ActivateCampaignController activateCampaignController = loader.getController();

            Stage stage = (Stage) category.getScene().getWindow();
            stage.setScene(newScene);
            activateCampaignController.warningLabel.setTextFill(Color.GREEN);
            activateCampaignController.warningLabel.setText("Campagna attivata");

        }

        warningLabel.setTextFill(Color.RED);
        warningLabel.setText("Dati errati");

    }

    private boolean checkValidity() {
        if (startCampaign.getValue().isBefore(LocalDate.now()) ||
                Integer.parseInt(availableVaccines.getText()) <= 0 ||
                vaccineName.getText().isEmpty() || availableCampaigns.getSelectionModel().getSelectedItem() == null ||
                clinicsAvailable.getSelectionModel().getSelectedItem() == null ||
                category.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        return true;
    }


    public void goBack(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/operatorLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        OperatorLoggedController operatorController = loader.getController();

        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }


}
