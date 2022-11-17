package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OperatorLoggedController {

    @FXML
    private Button insertNewCampaign;
    @FXML
    private Button activateCampaign;
    @FXML
    private Button logOut;


    public void createNewCampaign(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/insertCampaign.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        InsertCampaignController insertCampaignController = loader.getController();

        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    public void activateExistingCampaign(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/activateCampaign.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        ActivateCampaignController activateCampaignController = loader.getController();

        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    public void goBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/logIn.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        LogInController logInController = loader.getController();

        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    public void updateAvailability(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/updateCampaign.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        UpdateCampaignController updateCampaignController = loader.getController();

        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }

    public void showActiveCampaigns(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/activeCampaigns.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        ActiveCampaignsController activeCampaignsController = loader.getController();

        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }
}
