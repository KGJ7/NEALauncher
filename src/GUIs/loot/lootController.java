package GUIs.loot;

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
import java.util.Random;

public class lootController {
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
    private Label friendsListLabel;
    @FXML
    private Label welcomeGiftLabel;
    @FXML
    private Accordion friendsListAccordion;
    @FXML
    private ComboBox championClassComboBox;
    @FXML
    private TextField championSearchTextField;
    @FXML
    private CheckBox showOwnedCheckBox;
    @FXML
    private ContextMenu itemInteractionContextMenu;

    private String champRedeemToString;

    public void initialize() throws SQLException{
        initializeUserLevel();
        initializeUserMP();
        initializeComboBox();
        initializeContextMenu();
    }
    @FXML
    public void initializeComboBox(){
        ObservableList<String> championClassComboBoxOptions = FXCollections.observableArrayList("Fighter", "Tank", "Assassin", "Marksman", "Mage");
        championClassComboBox.getItems().addAll(championClassComboBoxOptions);
    }
    @FXML
    public void initializeContextMenu(){
        ContextMenu interactionContextMenu = new ContextMenu();
        MenuItem redeemItem = new MenuItem("Redeem item");
        MenuItem sellItem = new MenuItem("Sell item");
        interactionContextMenu.getItems().addAll(redeemItem, sellItem);
    }

    public boolean redeemItemCheck() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserCurrency FROM UserClientData WHERE DisplayName = ?";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1,loginController.currentUser);
            rs = ps.executeQuery();
            int userBalance = rs.getInt(1);
            if (userBalance >= 50){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }
    public void redeemItemTransaction() throws SQLException {
        redeemItemCheck();
        if (redeemItemCheck()){
            while (checkChampDuplicate()){
                randomLootReward();
                checkChampDuplicate();
                }
            }
        }

        public void redeemItemNotice(){

        }

    public void randomLootReward(){
        Random random = new Random();
        int champToRedeem;
        champToRedeem = random.nextInt(4);
        if(champToRedeem == 0){
            champRedeemToString = "Champ1";
        } else if (champToRedeem == 1){
            champRedeemToString = "Champ2";
        } else if (champToRedeem == 2){
            champRedeemToString = "Champ3";
        } else if (champToRedeem == 3){
            champRedeemToString = "Champ4";
        } else if (champToRedeem == 4){
            champRedeemToString = "Champ5";
        }
    }
    public boolean checkChampDuplicate() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql ="SELECT " + champRedeemToString + " FROM UserChampionData WHERE UserID = ?";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1,loginController.currentUserID);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;
            } else return false;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }

    public void sellItemTransaction() throws SQLException {
        PreparedStatement ps = null;
        String sql = "UPDATE UserClientData SET UserCurrency = UserCurrency + 30 WHERE DisplayName = ?";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1,loginController.currentUser);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            assert ps != null;
            ps.close();
        }
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
            rs.getInt("UserCurrency");
            displayCurrencyLabel.setText("MP:" + rs);
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
            rs.getInt("UserLevel");
            displayUserLevelLabel.setText("Level: " + rs);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            ps.close();
            rs.close();
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
