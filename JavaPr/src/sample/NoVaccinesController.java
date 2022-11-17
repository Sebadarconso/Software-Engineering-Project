package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NoVaccinesController implements Initializable {

    @FXML
    private Button beUpdated;
    @FXML
    private Button goBack;

    private User loggedUser;
    private Campaign campaign;


    CampaignDao campaignDB = CampaignDaoImpl.getInstance();
    UserDao userDB = UserDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    
    public void setData(User loggedUser, Campaign campaign) throws IOException {
        this.loggedUser = loggedUser;
        this.campaign = campaign;
    }

    public void closePop(ActionEvent actionEvent) {
        Stage stage = (Stage) beUpdated.getScene().getWindow();
        stage.close();
    }

    public void attach(ActionEvent actionEvent) throws IOException {

        if (!campaign.getObservers().contains(loggedUser)) {
            campaign.attach(loggedUser);
            campaignDB.updateCampaign(campaign);
        }

        Stage stage = (Stage) beUpdated.getScene().getWindow();
        stage.close();
    }
}