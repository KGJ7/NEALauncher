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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private ImageView welcomeGiftIV;

    private String champRedeemToString;
    private int hideOwnedChampCounter;
    private int hideCB;

    public void initialize() throws SQLException{
        Image image = new Image("/Images/teemoFloating.png");
        welcomeGiftIV = new ImageView(image);
        welcomeGiftIV.setFitHeight(159);
        welcomeGiftIV.setFitWidth(200);
        welcomeGiftIV.setPreserveRatio(true);
        welcomeGiftLabel.setGraphic(welcomeGiftIV);
        initializeUserLevel();
        initializeUserMP();
        initializeComboBox();
    }
    @FXML
    public void initializeComboBox(){
        ObservableList<String> championClassComboBoxOptions = FXCollections.observableArrayList("Fighter", "Tank", "Assassin", "Marksman", "Mage");
        championClassComboBox.getItems().addAll(championClassComboBoxOptions);
    }
    public void refresh(){
        if ((championClassComboBox.getValue().toString()) != "Champion class"){
            classComboBoxFilter();
        }
        if (championSearchTextField != null){
            search();
        }
    }

    public void search(){
        String searchBoxContents = championSearchTextField.getText();
        if (searchBoxContents == "Fighter"){
            hideCB = 1;
            hideItems();
        } else if (searchBoxContents == "Tank"){
            hideCB = 2;
            hideItems();
        } else if (searchBoxContents == "Assassin"){
            hideCB = 3;
            hideItems();
        } else if (searchBoxContents == "Marksman"){
            hideCB = 4;
            hideItems();
        } else if (searchBoxContents == "Mage"){
            hideCB = 5;
            hideItems();
        }
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
        if (redeemItemCheck()){
            randomLootReward();
            while (checkChampDuplicate()){
                randomLootReward();
                checkChampDuplicate();
            }
            addChampRedeemed();
            redeemItemUpdate();
            successfulRedeemItemNotice();
            welcomeGiftLabel.setVisible(false);
        } else failedRedeemItemNotice();
    }

        public void addChampRedeemed() throws SQLException {
            PreparedStatement ps = null;
            String sql = "UPDATE UserChampionData SET " + champRedeemToString +  " = 1 WHERE UserID = ?";
            try{
                Connection con = DBConnection.getConnection();
                assert con != null;
                ps = con.prepareStatement(sql);
                ps.setString(1,loginController.currentUserID);
                ps.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            } finally{
                assert ps != null;
                ps.close();
            }
        }

        public void redeemItemUpdate() throws SQLException {
            PreparedStatement ps = null;
            String sql = "UPDATE UserClientData SET UserCurrency = UserCurrency - 50 WHERE DisplayName = ?";
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
        public void failedRedeemItemNotice(){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed transaction!");
            alert.setContentText("Transaction failed, not enough money.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
        }
        public void successfulRedeemItemNotice(){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful transaction!");
            alert.setContentText("Transaction successful.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
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
        String sql ="SELECT * FROM UserChampionData WHERE UserID = ?";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1,loginController.currentUserID);
            rs = ps.executeQuery();
             return rs.getBoolean(champRedeemToString);
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
        sellItemNotice();
    }

    public void sellItemNotice(){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful transaction!");
            alert.setContentText("Transaction successful.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
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
    public void choiceBoxActions() throws SQLException {
        if(showOwnedCheckBox.isSelected()){
            welcomeGiftLabel.setVisible(true);
        } else if (!showOwnedCheckBox.isSelected()){
            hideOwned();
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
        welcomeGiftLabel.setVisible(false);
    }

    public void classComboBoxFilter(){
        String selectedFilter = championClassComboBox.getValue().toString();
        if (selectedFilter == "Fighter"){
            hideCB = 1;
            hideItems();
        } else if (selectedFilter == "Tank"){
            hideCB = 2;
            hideItems();
        } else if (selectedFilter == "Assassin"){
            hideCB = 3;
            hideItems();
        } else if (selectedFilter == "Marksman"){
            hideCB = 4;
            hideItems();
        } else if (selectedFilter == "Mage"){
            hideCB = 5;
            hideItems();
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
