package GUIs.preclient;

import DatabaseTools.DBConnection;
import com.sun.xml.internal.ws.api.message.Message;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    @FXML
    private Label emptyFieldErrorLabel;

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

    public boolean checkPasswordMatch() {
        if (passwordEntryField.getText().equals(reenterPasswordField.getText())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEmptyFields(){
        if (!emailTextField.getText().isEmpty()
            && !usernameTextField.getText().isEmpty()
            && !passwordEntryField.getText().isEmpty()
            && !reenterPasswordField.getText().isEmpty())
            return true;
        else return false;
    }
    public boolean checkEmailValidity(){
    return true;
    }

    public boolean checkPasswordValidity(){
    return true;
    }

    public boolean checkUserAvailability(){
        return true;
    }

    public String hashPassword(){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordEntryField.getText().getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest();
            StringBuffer hexPassword = new StringBuffer();
            for (byte hashedByte : hashedPassword ){
                hexPassword.append(Integer.toHexString(0xFF & hashedByte));
            }
            return hexPassword.toString();
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public boolean createNewUser(String username){
        String sql = "INSERT INTO UserClientData(DisplayName, UserPassword, UserLevel, UserXP) VALUES (?, ?, ? ,?)";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            String hashedPassword = hashPassword();
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.execute();
            con.close();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }
    }

    @FXML
    public void createAccount() throws SQLException {
        try {
            if(checkEmptyFields()){
                if (checkEmailValidity()){
                    if(checkPasswordValidity()){
                        if(checkUserAvailability()){
                            if(checkPasswordMatch()){
                                if(createNewUser(usernameTextField.getText())){

                                }
                            }else passwordsDoesntMatchLabel.setVisible(true);
                        }else invalidUsernameLabel.setVisible(true);
                    }else passwordSecurityReqNotMet.setVisible(true);
                }else invalidEmailLabel.setVisible(true);
            } else emptyFieldErrorLabel.setVisible(true);


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}
