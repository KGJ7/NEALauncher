package GUIs.lobby;

import DatabaseTools.DBConnection;
import GUIs.preclient.loginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class createLobbyController {
    @FXML
    private Button createLobbyButton;
    @FXML
    private Button openStatisticsTabButton;
    @FXML
    private Button openLootTabButton;
    @FXML
    private Button openStoreTabButton;
    @FXML
    private Button openInventoryTabButton;
    @FXML
    private Button openNewsTabButton;
    @FXML
    private Button openQnaTabButton;
    @FXML
    private Button normalGameModeButton;
    @FXML
    private Label displayCurrencyLabel;
    @FXML
    private Label displayUserLevelLabel;
    @FXML
    private Label gameModeDisplayLabel;
    @FXML
    private Button practiceToolModeButton;
    @FXML
    private Button confirmGameModeButton;

    private boolean gameModeSelected;
    private boolean gameTypeSelected;

    public void initialize() throws SQLException {
        initializeUserLevel();
        initializeUserMP();
    }


    @FXML
    public void initializeUserMP()throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserCurrency FROM UserClientData WHERE DisplayName = ?";
        try{
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUser);
            rs = ps.executeQuery();
            int userCurrency = rs.getInt("UserCurrency");
            displayCurrencyLabel.setText("MP:" + userCurrency);
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            ps.close();
            rs.close();
        }
    }
    @FXML
    public void initializeUserLevel() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserLevel FROM UserClientData WHERE DisplayName = ?";
        try{
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,loginController.currentUser);
            rs = ps.executeQuery();
            int userLevel = rs.getInt("UserLevel");
            displayUserLevelLabel.setText("Level: " + userLevel);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            ps.close();
            rs.close();
        }
    }

    public void normalGameModeSelected(){
        gameTypeSelected = true;
        gameModeSelected = false;
        System.out.println(gameModeSelected);
        gameModeDisplayLabel.setText("Normal game mode selected");
    }

    public void practiceToolSelected(){
        gameTypeSelected = true;
        gameModeSelected = true;
        gameModeDisplayLabel.setText("Practice tool selected");
    }

    public boolean lobbyTypeInput(){
        if(!gameTypeSelected){
            return false;
        } else return true;
    }

    public void createLobby(){
        if(lobbyTypeInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lobby alert!");
            alert.setContentText("Your lobby has been created.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
            openLobbySearchingTab();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a game mode.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
        }
    }

    public void openNewsTab(){
        try{
            Stage old = (Stage) openNewsTabButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/news/newsFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/News.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openLootTab(){
        try{
            Stage old = (Stage) openLootTabButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/loot/lootFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Loot.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openInventoryTab(){
        try{
            Stage old = (Stage) openInventoryTabButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/inventory/inventoryFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Inventory.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openStoreTab(){
        try{
            Stage old = (Stage) openStoreTabButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/shop/shopFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Store.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openStatisticsTab(){
        try{
            Stage old = (Stage) openStatisticsTabButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/statistics/statisticsFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Statistics.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openQNATab(){
        try{
            Stage old = (Stage) openQnaTabButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/qna/qnaFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/QNA.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openCreateLobbyTab(){
        try{
            Stage old = (Stage) createLobbyButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/lobby/createLobbyFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Lobby.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openLobbySearchingTab(){
        try{
            Stage old = (Stage) confirmGameModeButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/GUIs/lobby/lobbySearchingWindowFXML.fxml").openStream());
            Scene scene = new Scene(root, 1664, 936);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Lobby.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Client");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
