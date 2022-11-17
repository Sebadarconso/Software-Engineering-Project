package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;

public class BookingController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label cfLabel;
    @FXML
    private Label ctgLabel;
    @FXML
    private Label campaignName;
    @FXML
    private Label vaccinesAvailability;
    @FXML
    private Label vaccineName;
    @FXML
    private Label startCampaign;
    @FXML
    private ComboBox clinicsSelection;
    @FXML
    private Label clinicInfo;
    @FXML
    private DatePicker datePickerClinic;

    private Campaign campaign;
    private final ObservableList<String> clinicsList = FXCollections.observableArrayList();
    private User loggedUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePickerClinic) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        ClinicDao clinicDB = ClinicDaoImpl.getInstance();
                        Set<Clinic> clinics = ClinicDaoImpl.getInstance().getAllClinics();
                         if (clinicsSelection.getSelectionModel().getSelectedItem()!=null) {

                             if (item.isBefore(LocalDate.now()) || item.isAfter(campaign.getStartCampaign().plusMonths(3)) || item.isEqual(LocalDate.now())) {
                                 setStyle("-fx-background-color: #F3EAB2;");
                                 setDisable(true);
                             }

                             /*
                             if (item.isAfter(campaign.getStartCampaign().plusMonths(3))) {
                                 setStyle("-fx-background-color: #ffc0cb;");
                                 setDisable(true);
                             }

                              */

                             for (LocalDate d : ClinicDaoImpl.getInstance().getClinic(clinicsSelection.getSelectionModel().getSelectedItem().toString()).getBusyDays(campaign)) {
                                 if (item.equals(d)) {
                                     setStyle("-fx-background-color: #ffc0cb;");
                                     setDisable(true);
                                 }
                             }
                         }

                        //if flag is true and date is within range, set style



                    }
                };

            }

        };

        datePickerClinic.setDayCellFactory(dayCellFactory);
    }

    public void setData(Campaign campaign,User loggedUser) {
        this.campaign = campaign;

        for(Clinic cln:campaign.getClinics()) {
            clinicsList.add(cln.getName());
        }
        clinicsList.add(null);

        clinicsSelection.setItems(clinicsList);
        campaignName.setText(campaign.getName());
        vaccinesAvailability.setText(String.valueOf(campaign.getAvailability()));
        vaccineName.setText(campaign.getVaccine());
        startCampaign.setText(campaign.getStartCampaign().toString().split("-")[2] + "/" + campaign.getStartCampaign().toString().split("-")[1] + "/" + campaign.getStartCampaign().toString().split("-")[0]);

        clinicsSelection.valueProperty().addListener((observable, oldValue, newValue) -> {
            //clinicInfo.setText(campaign.getName());
            if (clinicsSelection.getSelectionModel().getSelectedItem()==null) {
                clinicInfo.setStyle("-fx-text-fill: RED");
                clinicInfo.setText("Seleziona un ambulatorio valido!!");

            } else {
                datePickerClinic.show();
            }
        });
        this.loggedUser = loggedUser;

        nameLabel.setText(loggedUser.getName());
        surnameLabel.setText(loggedUser.getSurname());
        dateLabel.setText(loggedUser.getDateBirth().toString().split("-")[2] + "/" + loggedUser.getDateBirth().toString().split("-")[1] + "/" + loggedUser.getDateBirth().toString().split("-")[0]);
        cityLabel.setText(loggedUser.getPlaceBirth());
        cfLabel.setText(loggedUser.getCF());

    }

    public void datePicked(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/timeSelection.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);

        int[] timeSlots = new int[16];
        Arrays.fill(timeSlots,0);
        Clinic clinic = ClinicDaoImpl.getInstance().getClinic(clinicsSelection.getSelectionModel().getSelectedItem().toString());


        TimeSelectionController timeSelectionController = loader.getController();
        timeSelectionController.setData(loggedUser, clinic, datePickerClinic.getValue(), campaign);

        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }
    public void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/userLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        UserLoggedController userLoggedController = loader.getController();

        userLoggedController.setData(loggedUser);
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }
}
