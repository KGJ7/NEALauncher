package GUIs.inventory;

import DatabaseTools.DBConnection;
import GUIs.preclient.loginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inventoryController {
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
    private Label displayCurrencyLabel;
    @FXML
    private Label displayUserLevelLabel;
    @FXML
    private Label fighterLabel;
    @FXML
    private Label tankLabel;
    @FXML
    private Label assassinLabel;
    @FXML
    private Label marksmanLabel;
    @FXML
    private Label mageLabel;
    @FXML
    private Label friendsListLabel;
    @FXML
    private Accordion friendsListAccordion;
    @FXML
    private ComboBox championClassComboBox;
    @FXML
    private TextField inventorySearchTextField;
    @FXML
    private CheckBox showUnownedCheckBox;
    private int hideOwnedChampCounter;
    public void initialize() throws SQLException {
        initializeUserLevel();
        initializeUserMP();
        initalizeComboBox();
    }

    @FXML
    public void initalizeComboBox(){
        ObservableList<String> championClassComboBoxOptions = FXCollections.observableArrayList("Fighter", "Tank", "Assassin", "Marksman", "Mage");
        championClassComboBox.getItems().addAll(championClassComboBoxOptions);
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
    @FXML
    public void hideOwned() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM UserChampionData WHERE UserID = ?";
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUserID);
            rs = ps.executeQuery();
            while (rs.next()){
                for (hideOwnedChampCounter = 1; hideOwnedChampCounter < 6; hideOwnedChampCounter++) {
                    if (rs.getBoolean("Champ" + hideOwnedChampCounter)) {
                        hideItems();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            assert ps !=null;
            ps.close();
            assert rs!= null;
            rs.close();
        }
    }
    public void hideItems(){
        if(hideOwnedChampCounter == 1){
            fighterLabel.setVisible(false);
        } if (hideOwnedChampCounter == 2 ){
            tankLabel.setVisible(false);
        } if (hideOwnedChampCounter == 3){
            assassinLabel.setVisible(false);
        } if (hideOwnedChampCounter == 4 ){
            marksmanLabel.setVisible(false);
        } if (hideOwnedChampCounter == 5 ){
            mageLabel.setVisible(false);
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
}
