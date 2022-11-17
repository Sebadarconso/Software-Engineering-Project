package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class NotificationsController {

    @FXML
    private ListView<String> listViewCamp;
    @FXML
    private Label notificationLabel;

    private final ObservableList<String> campaignList = FXCollections.observableArrayList();
    private User loggedUser;

    public void setData(User loggedUser) {
        this.loggedUser = loggedUser;

        for (Campaign cmp:loggedUser.subject) {
            if (cmp.isActive() && cmp.getAvailability() > 0) {
                campaignList.add(cmp.getName());
            }
        }

        if (campaignList.isEmpty()) {
            notificationLabel.setText("Nessuna nuova campagna disponibile");
            listViewCamp.setOpacity(0);
        } else {
            notificationLabel.setText("Hai delle nuove campagne disponibili");
            listViewCamp.setOpacity(100);
        }

        listViewCamp.setItems(campaignList);
    }


}
