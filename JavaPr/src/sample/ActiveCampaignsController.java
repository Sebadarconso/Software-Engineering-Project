package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActiveCampaignsController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private Label vaccineLabel;
    @FXML
    private Label availabilityLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label categoriesLabel;
    @FXML
    private Label clinicsLabel;
    @FXML
    private ComboBox comboActiveCampaigns;

    private Campaign selectedCampaign;
    private final ObservableList<String> campaignList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Campaign cmp:CampaignDaoImpl.getInstance().getAllCampaigns()) {
            if (cmp.isActive()) {
                campaignList.add(cmp.getName());
            }
        }

        comboActiveCampaigns.setItems(campaignList);

    }

    public void campaignSelected(ActionEvent actionEvent) {
        selectedCampaign = CampaignDaoImpl.getInstance().getCampaign(comboActiveCampaigns.getSelectionModel().getSelectedItem().toString());

        nameLabel.setText(selectedCampaign.getName());
        vaccineLabel.setText(selectedCampaign.getVaccine());
        availabilityLabel.setText(String.valueOf(selectedCampaign.getAvailability()));
        startLabel.setText(selectedCampaign.getStartCampaign().toString().split("-")[2] + "/" + selectedCampaign.getStartCampaign().toString().split("-")[1] + "/" + selectedCampaign.getStartCampaign().toString().split("-")[0]);
        for (CategorieCittadini ctg:selectedCampaign.getCategories()) {
            categoriesLabel.setText(categoriesLabel.getText() + "\n" + CategorieCittadini.toString(ctg));
        }
        for (Clinic cln:selectedCampaign.getClinics()) {
            clinicsLabel.setText(clinicsLabel.getText() + "\n" + cln.getName());
        }
    }

    public void goBack(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/operatorLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);

        Stage stage = (Stage) clinicsLabel.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }
}
