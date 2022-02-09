package GUIs.shop;

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
import java.util.Objects;

public class shopController {
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
    private Button searchButton;
    @FXML
    private Label backgroundLabel;
    @FXML
    private Label displayCurrencyLabel;
    @FXML
    private Label displayUserLevelLabel;
    @FXML
    private Label friendsListLabel;
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
    private ScrollPane friendsListScrollPane;
    @FXML
    private ComboBox championClassComboBox;
    @FXML
    private TextField championSearchTextField;
    @FXML
    private CheckBox showOwnedCheckBox;
    @FXML
    private ContextMenu itemInteractionContextMenu;

    private String champToBuy;
    private int hideOwnedChampCounter;
    private int hideCB;
    private boolean checkBoxPreviouslyChecked;

    public void initialize() throws SQLException {
        initializeUserLevel();
        initializeUserMP();
        initializeComboBox();
    }

    @FXML
    public void initializeComboBox() {
        ObservableList<String> championClassComboBoxOptions = FXCollections.observableArrayList("Fighter", "Tank", "Assassin", "Marksman", "Mage");
        championClassComboBox.getItems().addAll(championClassComboBoxOptions);
    }

    @FXML
    public void initializeUserMP() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserCurrency FROM UserClientData WHERE DisplayName = ?";
        try {
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUser);
            rs = ps.executeQuery();
            int userCurrency = rs.getInt("UserCurrency");
            displayCurrencyLabel.setText("MP:" + userCurrency);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            rs.close();
        }
    }

    @FXML
    public void initializeUserLevel() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserLevel FROM UserClientData WHERE DisplayName = ?";
        try {
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUser);
            rs = ps.executeQuery();
            int userLevel = rs.getInt("UserLevel");
            displayUserLevelLabel.setText("Level: " + userLevel);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            rs.close();
        }
    }

    public boolean searchFieldCheck() {
        if (championSearchTextField.getText().isEmpty()) {
            System.out.println("false");
            return false;
        } else
            System.out.println("true");
        return true;
    }

    public void search() {
        fighterLabel.setVisible(true);
        tankLabel.setVisible(true);
        assassinLabel.setVisible(true);
        marksmanLabel.setVisible(true);
        mageLabel.setVisible(true);
        String searchBoxContents = championSearchTextField.getText();
        String comboBoxContents = (String) championClassComboBox.getValue();
        if (searchFieldCheck()) {
            if (Objects.equals(searchBoxContents, "Fighter")) {
                hideCB = 1;
                hideChamp();
            } else if (Objects.equals(searchBoxContents, "Tank")) {
                hideCB = 2;
                hideChamp();
            } else if (Objects.equals(searchBoxContents, "Assassin")) {
                hideCB = 3;
                hideChamp();
            } else if (Objects.equals(searchBoxContents, "Marksman")) {
                hideCB = 4;
                hideChamp();
            } else if (Objects.equals(searchBoxContents, "Mage")) {
                hideCB = 5;
                hideChamp();
            } else {
                System.out.println("Searchbox empty");
            }
        } else if (comboBoxFieldCheck()) {
            classComboBoxFilter();
        }
    }

    public boolean comboBoxFieldCheck() {
        if (championClassComboBox.getValue().toString() != null) {
            return true;
        } else return false;
    }

    public void buyChampTransaction() throws SQLException {
        if (buyChampCheck()) {
            if (checkChampDuplicate()) {
                duplicateChampNotice();
            } else {
                addChamp();
                successfulTransactionNotice();
            }
        } else failedTransactionNotice();
    }

    public void duplicateChampNotice() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Failed transaction!");
        alert.setContentText("You already own this champion, why would you buy them twice?");
        alert.showAndWait().ifPresent((btnType) -> {
        });
    }

    public void failedTransactionNotice() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Failed transaction!");
        alert.setContentText("You don't have enough money to make this purchase, come back when you have over 100 MP!");
        alert.showAndWait().ifPresent((btnType) -> {
        });
    }

    public void successfulTransactionNotice() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful transaction!");
        alert.setContentText("Transaction successful!");
        alert.showAndWait().ifPresent((btnType) -> {
        });
    }

    public void addChamp() throws SQLException {
        PreparedStatement ps = null;
        String sql = "UPDATE UserChampionData SET " + champToBuy + " = true WHERE UserID = ?";
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUserID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert ps != null;
            ps.close();
        }
    }

    public void buyChamp1() throws SQLException {
        champToBuy = "Champ1";
        buyChampTransaction();
    }

    public void buyChamp2() throws SQLException {
        champToBuy = "Champ2";
        buyChampTransaction();
    }

    public void buyChamp3() throws SQLException {
        champToBuy = "Champ3";
        buyChampTransaction();
    }

    public void buyChamp4() throws SQLException {
        champToBuy = "Champ4";
        buyChampTransaction();
    }

    public void buyChamp5() throws SQLException {
        champToBuy = "Champ5";
        buyChampTransaction();
    }

    public boolean buyChampCheck() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT UserCurrency FROM UserClientData WHERE DisplayName = ?";
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUser);
            rs = ps.executeQuery();
            int userBalance = rs.getInt(1);
            if (userBalance >= 100) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }

    public boolean checkChampDuplicate() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM UserChampionData WHERE UserID = ?";
        try {
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUserID);
            rs = ps.executeQuery();
            return rs.getBoolean(champToBuy);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }

    @FXML
    public void choiceBoxActions() throws SQLException {
        if(showOwnedCheckBox.isSelected()){
            hideOwned();
        } else if (!showOwnedCheckBox.isSelected()){
            fighterLabel.setVisible(true);
            tankLabel.setVisible(true);
            assassinLabel.setVisible(true);
            marksmanLabel.setVisible(true);
            mageLabel.setVisible(true);
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
                        hideChamp();
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

    public void hideChamp(){
        if(hideOwnedChampCounter == 1 || hideCB != 1){
            fighterLabel.setVisible(false);
        } if (hideOwnedChampCounter == 2 || hideCB != 2){
            tankLabel.setVisible(false);
        } if (hideOwnedChampCounter == 3 || hideCB != 3){
            assassinLabel.setVisible(false);
        } if (hideOwnedChampCounter == 4 || hideCB != 4){
            marksmanLabel.setVisible(false);
        } if (hideOwnedChampCounter == 5 || hideCB != 5){
            mageLabel.setVisible(false);
        }
    }

    public void classComboBoxFilter(){
        String selectedFilter = championClassComboBox.getValue().toString();
        System.out.println(selectedFilter);
        if (selectedFilter == "Fighter"){
            hideCB = 1;
            hideChamp();
        } else if (selectedFilter == "Tank"){
            hideCB = 2;
            hideChamp();
        } else if (selectedFilter == "Assassin"){
            hideCB = 3;
            hideChamp();
        } else if (selectedFilter == "Marksman"){
            hideCB = 4;
            hideChamp();
        } else if (selectedFilter == "Mage"){
            hideCB = 5;
            hideChamp();
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
