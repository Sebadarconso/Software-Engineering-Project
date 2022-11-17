package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class InsertCampaignController {

    @FXML
    private Label warningText;
    @FXML
    private Button confirmButton;
    @FXML
    private Button logOut;
    @FXML
    private TextField campaignName;

    CampaignDao campaignDB = CampaignDaoImpl.getInstance();

    public void logOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/operatorLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        OperatorLoggedController operatorController = loader.getController();

        Stage stage = (Stage) logOut.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    public void saveCampaign(ActionEvent actionEvent) throws IOException {
        Main m = new Main();

        if (checkValidity()) {
            Set<Campaign> campaignsSet = campaignDB.getAllCampaigns();
            Campaign newCampaign = new Campaign(campaignName.getText());
            if (campaignsSet.contains(newCampaign)) {

                warningText.setTextFill(Color.RED);
                warningText.setText("Campagna già presente");

            } else {

                campaignDB.addCampaign(newCampaign);

                campaignName.setText("");
                warningText.setTextFill(Color.GREEN);
                warningText.setText("Campagna inserita");

            }
        }
    }

    private boolean checkValidity() {
        if (campaignName.getText().isEmpty()) {
            warningText.setText("Il nome non può essere vuoto");
            return false;
        }
        return true;
    }
}
