package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class PopUpPasswordController implements Initializable {

    @FXML
    private TextField cfField;
    @FXML
    private TextField numField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label warningLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Text usernameText;
    @FXML
    private Text passwordText;

    private String cf;
    private Set<User> users = UserDaoImpl.getInstance().getAllUsers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cfField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

    }


    public void getPassword(ActionEvent actionEvent) {
        cf = cfField.getText();
        boolean userFound = false;


        for (User user:users) {
            if (cf.equals(user.getCF()) && numField.getText().equals(user.getHealthCard())) {
                if (user.getRegistered()) {
                    usernameText.setOpacity(100);
                    passwordText.setOpacity(100);
                    warningLabel.setOpacity(0);
                    usernameLabel.setText(user.getUsername());
                    passwordLabel.setText(user.getPassword());
                    userFound = true;
                }
            }
        }

        if (!userFound) {
            usernameText.setOpacity(0);
            passwordText.setOpacity(0);
            warningLabel.setOpacity(100);
            usernameLabel.setText("");
            passwordLabel.setText("");
        }

    }

}
