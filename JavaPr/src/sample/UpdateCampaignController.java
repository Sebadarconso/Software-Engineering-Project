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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCampaignController implements Initializable {

    @FXML
    private ComboBox<String> campaign;
    @FXML
    private TextField newAvailability;

    private final ObservableList<String> campaignList = FXCollections.observableArrayList();
    CampaignDao campaignDB = CampaignDaoImpl.getInstance();
    UserDao userDB = UserDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newAvailability.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                newAvailability.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        for (Campaign cmp:campaignDB.getAllCampaigns()) {
            if (cmp.isActive())
                campaignList.add(cmp.getName());
        }

        campaign.setItems(campaignList);

    }

    public void confirmAvailability(ActionEvent actionEvent) throws IOException {
        Campaign campaignToUpdate = campaignDB.getCampaign(campaign.getSelectionModel().getSelectedItem());
        campaignToUpdate.setAvailability(Integer.parseInt(newAvailability.getText()) + campaignToUpdate.getAvailability());
        campaignDB.updateCampaign(campaignToUpdate);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/operatorLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        OperatorLoggedController operatorLoggedController = loader.getController();

        Stage stage = (Stage) campaign.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/operatorLogged.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        OperatorLoggedController operatorLoggedController = loader.getController();

        Stage stage = (Stage) campaign.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }

}
