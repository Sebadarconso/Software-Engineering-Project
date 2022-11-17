package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader("src/campaigns.csv"));
        String recordCampaign = reader.readLine();
        while(recordCampaign != null) {
            String[] fieldRec = recordCampaign.split(",");
            Campaign campaignToUpdate = CampaignDaoImpl.getInstance().getCampaign(fieldRec[0]);
            campaignToUpdate.setObservers(User.parseUsers(fieldRec[6]));
            CampaignDaoImpl.getInstance().updateCampaign(campaignToUpdate);
            recordCampaign = reader.readLine();
        }

        ReservationAPI reservationDB = new ReservationAPIImpl();
        for (Clinic cln:ClinicDaoImpl.getInstance().getAllClinics()) {
            reservationDB.setReservation(cln);
            ClinicDaoImpl.getInstance().updateClinic(cln);
        }

        for (User usr:UserDaoImpl.getInstance().getAllUsers()) {
            if (usr.getRegistered()) {
                reservationDB.setReservation(usr);
                UserDaoImpl.getInstance().updateUser(usr);
            }
        }

        reservationDB.refreshReservations();

        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/logIn.fxml"));
        //primaryStage.setTitle("vaccino");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void changeScene(String fxml, String title, int v, int v1) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Scene newScene = new Scene(pane, v, v1);
        stg.setTitle(title);
        stg.setScene(newScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}