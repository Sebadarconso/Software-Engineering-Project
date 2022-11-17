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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserLoggedController implements Initializable {


    @FXML
    private ImageView notificationIcon;
    @FXML
    private Label ctgLabel;
    @FXML
    private Button storicoPren;
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
    private Button confirmButton;
    @FXML
    private ComboBox activeCampaigns;
    @FXML
    private Label warningLabel;

    private User loggedUser;
    private final ObservableList<String> campaignList = FXCollections.observableArrayList();
    CampaignDao campaignDB = CampaignDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setData(User loggedUser) throws IOException {
        this.loggedUser = loggedUser;

        for (Campaign cmp:campaignDB.getAllCampaigns()) {
            if (cmp.getCategories().containsAll(loggedUser.getCategory()) && cmp.isActive()) {
                campaignList.add(cmp.getName());
            }
        }

        activeCampaigns.setItems(campaignList);
        nameLabel.setText(loggedUser.getName());
        surnameLabel.setText(loggedUser.getSurname());
        dateLabel.setText(loggedUser.getDateBirth().toString().split("-")[2] + "/" + loggedUser.getDateBirth().toString().split("-")[1] + "/" + loggedUser.getDateBirth().toString().split("-")[0]);
        cityLabel.setText(loggedUser.getPlaceBirth());
        cfLabel.setText(loggedUser.getCF());
        for (CategorieCittadini ctg:loggedUser.getCategory()) {
            ctgLabel.setText(ctgLabel.getText() + "\n" + CategorieCittadini.toString(ctg));
        }

        InputStream stream = new FileInputStream("/Users/sebastianodarconso/Desktop/IngegneriaDelSoftware-PROGETTO/JavaPr/src/img/notificationBellR.png");
        Image image = new Image(stream);

        for (Campaign cmp:loggedUser.getSubject()) {
            if(!loggedUser.getSubject().isEmpty()) {
                if (cmp.isActive() && cmp.getAvailability() > 0) {
                    notificationIcon.setImage(image);
                }
            }
        }
    }

    public void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/logIn.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        LogInController logInController = loader.getController();

        Stage stage = (Stage) storicoPren.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }


    public void confirmCampaign(ActionEvent actionEvent) throws IOException {

        boolean alreadyBooked = false;

        if (activeCampaigns.getSelectionModel().getSelectedItem() == null) {
            warningLabel.setText("Scegli una campagna");
        } else {
            for (UserReservation rsv : loggedUser.getReservations()) {
                if (rsv.getCampaign().equals(campaignDB.getCampaign(activeCampaigns.getSelectionModel().getSelectedItem().toString()))) {
                    warningLabel.setTextFill(Color.RED);
                    warningLabel.setText("Hai già una prenotazione\n per questa campagna");
                    alreadyBooked = true;
                }
            }

            if (!alreadyBooked) {
                if (campaignDB.getCampaign(activeCampaigns.getSelectionModel().getSelectedItem().toString()).getAvailability() > 0) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../fxml/booking.fxml"));
                    Parent root = loader.load();
                    Scene newScene = new Scene(root, 600, 400);
                    BookingController bookingController = loader.getController();
                    bookingController.setData(campaignDB.getCampaign(activeCampaigns.getSelectionModel().getSelectedItem().toString()), loggedUser);

                    Stage stage = (Stage) confirmButton.getScene().getWindow();
                    stage.close();
                    Stage newStage = new Stage();
                    newStage.setScene(newScene);
                    newStage.show();

                } else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../fxml/noVaccines.fxml"));
                    Parent root = loader.load();
                    Scene newScene = new Scene(root, 300, 150);
                    Stage currentStage = (Stage) activeCampaigns.getScene().getWindow();

                    NoVaccinesController noVaccinesController = loader.getController();
                    noVaccinesController.setData(loggedUser, campaignDB.getCampaign(activeCampaigns.getSelectionModel().getSelectedItem().toString()));

                    Stage popUp = new Stage();
                    popUp.initModality(Modality.WINDOW_MODAL);
                    popUp.initOwner(currentStage);
                    popUp.setTitle("Attenzione");
                    popUp.setScene(newScene);
                    popUp.show();
                }
            }
        }

    }

    public void viewReservations(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/pastReservations.fxml"));
        Parent root = loader.load();
        PastReservationsController pastReservationsController = loader.getController();
        pastReservationsController.setData(loggedUser);
        Scene newScene = new Scene(root, 430, 400);
        Stage currentStage = (Stage) activeCampaigns.getScene().getWindow();


        Stage popUp = new Stage();
        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(currentStage);
        popUp.setTitle("Notifiche");
        popUp.setScene(newScene);
        popUp.show();

    }

    public void viewNotifications() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/notifications.fxml"));
        Parent root = loader.load();
        NotificationsController notificationsController= loader.getController();
        notificationsController.setData(loggedUser);
        Scene newScene = new Scene(root, 400, 200);
        Stage currentStage = (Stage) activeCampaigns.getScene().getWindow();


        Stage popUp = new Stage();
        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(currentStage);
        popUp.setTitle("Notifiche");
        popUp.setScene(newScene);
        popUp.show();

    }

    public void setConfirmLabel() {
        warningLabel.setTextFill(Color.GREEN);
        warningLabel.setText("Campagna prenotata con successo");
    }

}