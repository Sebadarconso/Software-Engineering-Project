package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterUserController implements Initializable {
    
    @FXML
    private Button saveButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField cfField;
    @FXML
    private TextField hcField;
    @FXML
    private TextField placeOfBirthField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private DatePicker dateOfBirthField;
    @FXML
    private Label warningLabel;
    @FXML
    private Button logOut;

    UserDao userDB = UserDaoImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hcField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                hcField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        cfField.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
    }

    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/logIn.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 400);
        LogInController logInController = loader.getController();

        Stage stage = (Stage) warningLabel.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }
    
    public void saveUser(ActionEvent event) throws IOException {

        Main m = new Main();

        if (checkValidity()) {
            User newUser = userDB.getUser(cfField.getText());
            newUser.setUsername(usernameField.getText());
            newUser.setPassword(passwordField.getText());
            newUser.setRegistered(true);
            userDB.updateUser(newUser);

            String path = "src/userReservations/" + newUser.getUsername() + ".csv";
            File file = new File(path);
            file.createNewFile();;
            logOut(new ActionEvent());
        }
    }

    public boolean checkValidity() {


        User newUser = new User();
        newUser.setName(nameField.getText());
        newUser.setSurname(surnameField.getText());
        newUser.setCF(cfField.getText());
        newUser.setHealthCard(hcField.getText());
        newUser.setDateBirth(dateOfBirthField.getValue());
        newUser.setPlaceBirth(placeOfBirthField.getText());

        boolean userContained = userDB.getAllUsers().contains(newUser);
        if (userContained) {

            if (userDB.getUser(cfField.getText()).getRegistered()) {
                warningLabel.setText("Utente già registrato");
                return false;
            } else if (usernameField.getText().length() <= 5 || passwordField.getText().length() <= 5) {
                warningLabel.setText("Password e username devono essere lunghe almeno 5 caratteri");
                return false;
            } else if (userDB.getUserByUsr(usernameField.getText()) != null){
                warningLabel.setText("Username già in uso");
                return false;
            } else {
                return true;
            }
        }

        warningLabel.setText("Anagrafica non valida, utente non presente" + "\n" + "    Per info contattare: prova@prova.it");
        return false;

    }


}
