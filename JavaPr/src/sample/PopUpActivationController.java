package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PopUpActivationController {

    @FXML
    private Label warningLabel;
    @FXML
    private DatePicker datePicker;

    private Campaign campaign;
    private ActivateCampaignController prevStage;

    public void setData(Campaign campaign, ActivateCampaignController prevStage) {
        this.campaign = campaign;
        this.prevStage = prevStage;
    }

    public void confirmDate(ActionEvent actionEvent) throws IOException {
        if (datePicker.getValue().isBefore(LocalDate.now())) {
            warningLabel.setText("Data non valida");
        } else {
            campaign.setStartCampaign(datePicker.getValue());
            CampaignDaoImpl.getInstance().updateCampaign(campaign);

            Stage stage = (Stage) warningLabel.getScene().getWindow();
            stage.close();
            prevStage.goBack(new ActionEvent());
        }
    }
}
