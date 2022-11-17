package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

public class LogInController {

    public LogInController() {

    }

    @FXML
    private Button button;
    @FXML
    private Button cancelButton;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private RadioButton showPassword;
    @FXML
    private Button newUserButton;
    @FXML
    private Button forgotPassword;

    UserDao userDB = UserDaoImpl.getInstance();
    private Stage stg;

    public void userLogIn(ActionEvent event) throws IOException {
        checkLogin();
    }

    public void registerNewUser(ActionEvent event) throws IOException {
        createNewUser();
    }

    private void createNewUser() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/registration.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        RegisterUserController registerUserController = loader.getController();

        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();

    }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/logIn.fxml", "vaccino", 600, 450);
    }

    private void checkLogin() throws IOException {

        for (User usr:userDB.getAllUsers()) {

            if (username.getText().equals(usr.getUsername()) && password.getText().equals(usr.getPassword()) && usr.getRegistered()) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/userLogged.fxml"));
                Parent root = loader.load();
                Scene newScene = new Scene(root, 600, 400);
                UserLoggedController userController = loader.getController();
                userController.setData(usr);

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.show();

            }
        }

        if (username.getText().equals("root") && password.getText().equals("password")) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/operatorLogged.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root, 600, 400);
            OperatorLoggedController userController = loader.getController();

            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();

        }

        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            wrongLogIn.setText("Inserisci username e password");
        } else {
            wrongLogIn.setText("Username o password errati");
        }
    }

    public void ShowPassword(ActionEvent actionEvent) throws IOException {

        if(showPassword.isSelected()) {
            password.setPromptText(password.getText());
            password.setText("");
            password.setDisable(false);
        } else {
            password.setText(password.getPromptText());
            password.setPromptText("");
            password.setDisable(false);
        }

    }
    public void ForgotPassword(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/popUpPassword.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 400, 250);
        Stage currentStage = (Stage) forgotPassword.getScene().getWindow();

        //PopUpPasswordController popUpPasswordController = loader.getController();
        //popUpPasswordController.setData();

        Stage popUp = new Stage();
        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(currentStage);
        popUp.setTitle("Recupero password");
        popUp.setScene(newScene);
        popUp.show();

    }
}