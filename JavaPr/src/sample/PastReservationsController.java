package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;

public class PastReservationsController implements Initializable {


    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, Campaign> campaignColumn;
    @FXML
    private TableColumn<Reservation, Clinic> clinicColumn;
    @FXML
    private TableColumn<Reservation, LocalDate> dateColumn;
    @FXML
    private TableColumn<Reservation, String> timeColumn;
    @FXML
    private TableColumn<Reservation, Button> deleteColumn;


    private final ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private User loggedUser;
    ReservationAPI reservationDB = new ReservationAPIImpl();
    CampaignDao campaignDB = CampaignDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void setData(User loggedUser) {
        this.loggedUser = loggedUser;
        Set<UserReservation> res = loggedUser.getReservations();

        for(Reservation reserv : res) {
            reservationList.add(reserv);
            reserv.getDeleteButton().setOnAction(e -> {
                reservationList.remove(reserv);
                try {
                    reservationDB.deleteReservation(((UserReservation) reserv).getClinic(), new ClinicReservation(reserv.getCampaign(), reserv.getDate(), reserv.getTimeSlot(), loggedUser));
                    reservationDB.deleteReservation(loggedUser, (UserReservation) reserv);
                    reserv.getCampaign().setAvailability(reserv.getCampaign().getAvailability() + 1);
                    campaignDB.updateCampaign(reserv.getCampaign());

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }


        campaignColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Campaign>("campaign"));
        clinicColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Clinic>("clinic"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("timeSlot"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Button>("deleteButton"));

        reservationsTable.setItems(reservationList);







    }


    public void goBack(ActionEvent event) throws IOException {

    }
}
