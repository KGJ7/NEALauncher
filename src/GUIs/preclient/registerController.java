package GUIs.preclient;

import DatabaseTools.DBConnection;
import com.sun.xml.internal.ws.api.message.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerController{
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
    private Label passwordReqLabel;
    @FXML
    private Label neverSharePasswordLabel;
    @FXML
    private Label errorLabel;

    public void initialize(){
        errorLabel.setText("");
    }

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
        System.out.println(passwordEntryField.getText());
        System.out.println(reenterPasswordField.getText());
        return passwordEntryField.getText().equals(reenterPasswordField.getText());
    }

    public boolean checkEmptyFields() {
        if (!emailTextField.getText().isEmpty()
                && !usernameTextField.getText().isEmpty()
                && !passwordEntryField.getText().isEmpty()
                && !reenterPasswordField.getText().isEmpty())
            return true;
        else return false;
    }

    public boolean checkEmailValidity() {
        String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailTextField.getText());

        System.out.println(matcher.matches());
        if (!matcher.matches()) {
            errorLabel.setText("Invalid email address.");
        } else {
            errorLabel.setText("");
        }
        return matcher.matches();
    }

    public boolean passwordContainsDigit() {
        boolean containsDigit = false;
        for (char c : passwordEntryField.getText().toCharArray()) {
            if(Character.isDigit(c)){
                containsDigit = true;
            }
        } return containsDigit;
    }

    public boolean checkPasswordValidity() {
            checkPasswordLength();
            passwordContainsDigit();
        if (checkPasswordLength() && passwordContainsDigit()){
            return true;
        } else return false;
    }
    public boolean checkPasswordLength(){
        if (passwordEntryField.getText().length()>7){
            return true;
        } else return false;
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
    public boolean checkForExistingUser() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM UserClientData where DisplayName = ?";
        try{
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,usernameTextField.getText());
            rs = ps.executeQuery();
             return (!rs.next());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ps.close();
            rs.close();
        }
    }

    @FXML
    public void createAccount() throws SQLException {
        try {
            if(checkEmptyFields()){
                if (checkEmailValidity()){
                    if(checkPasswordValidity()){
                        if(checkPasswordMatch()){
                            if(checkForExistingUser()){
                                if(createNewUser(usernameTextField.getText())) {
                                    System.out.println("user created yay");
                                    }
                            }else errorLabel.setText("Username already taken!");
                        }else errorLabel.setText("Passwords don't match!");
                    }else errorLabel.setText("Password does not meet security requirements!");
                }else errorLabel.setText("Invalid email address!");
            } else errorLabel.setText("Please fill in all fields!");


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }


}
