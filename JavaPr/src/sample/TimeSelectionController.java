package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;

public class TimeSelectionController implements Initializable {

    @FXML
    private Button bt0;
    @FXML
    private Button bt1;
    @FXML
    private Button bt2;
    @FXML
    private Button bt3;
    @FXML
    private Button bt4;
    @FXML
    private Button bt5;
    @FXML
    private Button bt6;
    @FXML
    private Button bt7;
    @FXML
    private Button bt8;
    @FXML
    private Button bt9;
    @FXML
    private Button bt10;
    @FXML
    private Button bt11;
    @FXML
    private Button bt12;
    @FXML
    private Button bt13;
    @FXML
    private Button bt14;
    @FXML
    private Button bt15;
    @FXML
    private Label ctgLabel;
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

    private User loggedUser;
    private Clinic clinic;
    private LocalDate selectedDate;
    ClinicDao clinicDB = ClinicDaoImpl.getInstance();
    private Campaign campaign;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(User loggedUser, Clinic clinic, LocalDate selectedDate, Campaign campaign) throws IOException {
        this.loggedUser = loggedUser;
        this.clinic = clinic;
        this.selectedDate = selectedDate;
        this.campaign = campaign;


        nameLabel.setText(loggedUser.getName());
        surnameLabel.setText(loggedUser.getSurname());
        dateLabel.setText(loggedUser.getDateBirth().toString().split("-")[2] + "/" + loggedUser.getDateBirth().toString().split("-")[1] + "/" + loggedUser.getDateBirth().toString().split("-")[0]);
        cityLabel.setText(loggedUser.getPlaceBirth());
        cfLabel.setText(loggedUser.getCF());
        for (CategorieCittadini ctg:loggedUser.getCategory()) {
            ctgLabel.setText(ctgLabel.getText() + "\n" + CategorieCittadini.toString(ctg));
        }

        int timeSlots[] = new int[16];
        for (ClinicReservation rsv:clinic.getReservations()) {
            if (campaign.equals(rsv.getCampaign())) {
                switch (Reservation.formatTimeSlot(rsv.getTimeSlot())) {
                    case 0:
                        bt0.setDisable(true);
                        bt0.setOpacity(0);
                        break;
                    case 1:
                        bt1.setDisable(true);
                        bt1.setOpacity(0);
                        break;
                    case 2:
                        bt2.setDisable(true);
                        bt2.setOpacity(0);
                        break;
                    case 3:
                        bt3.setDisable(true);
                        bt3.setOpacity(0);
                        break;
                    case 4:
                        bt4.setDisable(true);
                        bt4.setOpacity(0);
                        break;
                    case 5:
                        bt5.setDisable(true);
                        bt5.setOpacity(0);
                        break;
                    case 6:
                        bt6.setDisable(true);
                        bt6.setOpacity(0);
                        break;
                    case 7:
                        bt7.setDisable(true);
                        bt7.setOpacity(0);
                        break;
                    case 8:
                        bt8.setDisable(true);
                        bt8.setOpacity(0);
                        break;
                    case 9:
                        bt9.setDisable(true);
                        bt9.setOpacity(0);
                        break;
                    case 10:
                        bt10.setDisable(true);
                        bt10.setOpacity(0);
                        break;
                    case 11:
                        bt11.setDisable(true);
                        bt11.setOpacity(0);
                        break;
                    case 12:
                        bt12.setDisable(true);
                        bt12.setOpacity(0);
                        break;
                    case 13:
                        bt13.setDisable(true);
                        bt13.setOpacity(0);
                        break;
                    case 14:
                        bt14.setDisable(true);
                        bt14.setOpacity(0);
                        break;
                    case 15:
                        bt15.setDisable(true);
                        bt15.setOpacity(0);
                        break;

                }
            }
        }
    }

    public void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/booking.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        BookingController bookingController = loader.getController();

        bookingController.setData(campaign, loggedUser);
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }


    public void timeSlotSelected(ActionEvent actionEvent) throws IOException {

        String newTimeSlot = ((Button) actionEvent.getSource()).getText();

        ReservationAPI reservationDB = new ReservationAPIImpl();
        reservationDB.addReservation(loggedUser, new UserReservation(campaign, selectedDate, newTimeSlot, clinic));
        reservationDB.addReservation(clinic, new ClinicReservation(campaign, selectedDate, newTimeSlot, loggedUser));
        loggedUser.getSubject().remove(campaign);
        UserDaoImpl.getInstance().updateUser(loggedUser);

        campaign.setAvailability(campaign.getAvailability() - 1);
        CampaignDaoImpl.getInstance().updateCampaign(campaign);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/userLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        UserLoggedController userLoggedController = loader.getController();

        userLoggedController.setData(loggedUser);
        userLoggedController.setConfirmLabel();
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }

}