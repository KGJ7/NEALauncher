package GUIs.preclient;

import DatabaseTools.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class loginController {

    private final loginModel LoginModel = new loginModel();
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
    @FXML
    private Label invalidLoginLabel;
    @FXML
    private Label confirmDBConLabel;
    @FXML
    private Label loginImageLabel;
    @FXML
    private Label logoLabel;
    @FXML
    private Label signInLabel;
    @FXML
    private Label backgroundLabel;


    public static String currentUser;
    public static String currentUserID;

    public void initialize(){
        if(this.LoginModel.isConnected()){
            confirmDBConLabel.setText("Database connection confirmed!");
        } else {
            confirmDBConLabel.setText("Database connection... not established? Hm...");
        }
        Image image = new Image("/Images/Space.jpg");
        bigImageOnSideOfWindow = new ImageView(image);
        bigImageOnSideOfWindow.setFitWidth(1204);
        bigImageOnSideOfWindow.setFitHeight(936);
        bigImageOnSideOfWindow.setPreserveRatio(false);
        loginImageLabel.setGraphic(bigImageOnSideOfWindow);
        Image image2 = new Image("/Images/teemoFloating.png");
        smallImageAboveText = new ImageView(image2);
        smallImageAboveText.setY(200);
        smallImageAboveText.setX(200);
        smallImageAboveText.setPreserveRatio(false);
        logoLabel.setGraphic(smallImageAboveText);

    }
    @FXML
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

    @FXML
    public void loginUser(){
        try{
            if(usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
               invalidLoginLabel.setText("Please fill in all fields.");
            }
            else if(LoginModel.isLogin(this.usernameTextField.getText(), this.passwordField.getText())) {
                currentUser = usernameTextField.getText();
                getUserID();
                Stage old = (Stage) loginButton.getScene().getWindow();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("/GUIs/news/newsFXML.fxml").openStream());
                Scene scene = new Scene(root, 1664, 936);
                scene.getStylesheets().add(getClass().getResource("/Stylesheets/Login.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Client");
                stage.setResizable(false);
                old.close();
                stage.show();
            }else invalidLoginLabel.setText("Invalid login!");
            } catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }
    public void getUserID() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserID FROM UserClientData WHERE DisplayName = ?";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1, currentUser);
            rs = ps.executeQuery();
            currentUserID = rs.getString("UserID");
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }
}



