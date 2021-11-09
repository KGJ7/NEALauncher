package GUIs.preclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class loginController {

    @FXML
    private ImageView smallImageAboveText;
    @FXML
    private ImageView bigImageOnSideOfWindow;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label newPlayerLabel;

    public void registerUser() {
        try {
            Stage old = (Stage) registerButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("RegisterFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Register.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Register a new account!");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//
//    }
//    @FXML
//    public void login(){
//        try{
//            if (this.loginModel.)
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


