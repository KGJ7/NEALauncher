package GUIs.preclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

}
