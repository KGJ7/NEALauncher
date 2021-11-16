package GUIs.preclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class registerController {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordEntryField;
    @FXML
    private PasswordField reenterPasswordField;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button loginScreenReturnButton;
    @FXML
    private Label registerYourAccount;
    @FXML
    private Label invalidEmailLabel;
    @FXML
    private Label invalidUsernameLabel;
    @FXML
    private Label passwordSecurityReqNotMet;
    @FXML
    private Label passwordsDoesntMatchLabel;
    @FXML
    private Label passwordReqLabel;
    @FXML
    private Label neverSharePasswordLabel;

    public void returnToLogin() {
        try {
            Stage old = (Stage) loginScreenReturnButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("loginFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Login.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Log in");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
