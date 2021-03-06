package GUIs.statistics;

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

public class statisticsController {
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
    private Label statisticsDisplayLabel;
    @FXML
    private ScrollPane friendsListScrollPane;
    @FXML
    private ComboBox timePeriodComboBox;
    @FXML
    private ComboBox statisticTypeComboBox;
    @FXML
    private TextField championSearchTextField;

    private String statTypeInput;
    private String statTypeQuery;
    private String statMost;
    private String statAverage;
    private String statTotal;


    public void initialize() throws SQLException {
        initializeUserLevel();
        initializeUserMP();
        initializeComboBoxes();
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
    public void displayStats() throws SQLException {
        statTypeInput = statisticTypeComboBox.getValue().toString();
        if (statTypeInput.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty search criteria!");
            alert.setContentText("Please enter a stat type to display.");
            alert.showAndWait().ifPresent((btnType) -> {
            });
        }else {
            getStats();
        }
    }

    @FXML
    public void getStats() throws SQLException {
        if (statisticTypeComboBox.getValue()=="Kills"){
            statTypeQuery = "Kills";
            statQuery();
            displayStatLabel();
        } else if (statisticTypeComboBox.getValue()=="Deaths"){
            statTypeQuery = "Deaths";
            statQuery();
            displayStatLabel();
        } else if (statisticTypeComboBox.getValue()=="Assists"){
            statTypeQuery = "Assists";
            statQuery();
            displayStatLabel();
        } else if (statisticTypeComboBox.getValue()== "Healing"){
            statTypeQuery = "Healing";
            statQuery();
            displayStatLabel();
        } else if (statisticTypeComboBox.getValue()=="KDA"){
            statTypeQuery = "KDA";
            statQueryKDA();
            displayStatLabel();
        } else
            System.out.println(statisticTypeComboBox.getValue());
            System.out.println("?");
    }
    @FXML
    public void displayStatLabel() throws SQLException {
        if (Objects.equals(statTypeQuery, "KDA")){
            statisticsDisplayLabel.setText(statQueryKDA());
        } else if (Objects.equals(statTypeQuery, "Kills")){
            statisticsDisplayLabel.setText("Most: " + statMost + "\n Average: " + statAverage + "\n Total:" + statTotal);
        } else if (Objects.equals(statTypeQuery, "Deaths")){
            statisticsDisplayLabel.setText("Most: " + statMost + "\n Average: " + statAverage + "\n Total:" + statTotal);
        } else if (Objects.equals(statTypeQuery, "Assists")){
            statisticsDisplayLabel.setText("Most: " + statMost + "\n Average: " + statAverage + "\n Total:" + statTotal);
        } else if (Objects.equals(statTypeQuery, "Healing")){
            statisticsDisplayLabel.setText("Most: " + statMost + "\n Average: " + statAverage + "\n Total:" + statTotal);
        } else System.out.println("display stat doesn't work");
    }

    public void statQuery() throws SQLException {
        PreparedStatement ps = null;
        String sql = "SELECT Most" + statTypeQuery + ", Average" + statTypeQuery + ", Total" + statTypeQuery +" FROM UserGameStatistics WHERE UserID = ?";
        try{
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUserID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statMost = rs.getString("Most" + statTypeQuery);
                statAverage = rs.getString("Average" + statTypeQuery);
                statTotal = rs.getString("Total" + statTypeQuery);
            }
        } catch (SQLException e ){
            e.printStackTrace();
        } finally{
            ps.close();
        }
    }
    public String statQueryKDA() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT KDA FROM UserGameStatistics WHERE UserID = ?";
        try{
            Connection con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, loginController.currentUserID);
            rs = ps.executeQuery();
            return (rs.toString());
        } catch (SQLException e ){
            e.printStackTrace();
            return null;
        } finally{
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

    public void initializeComboBoxes(){
        ObservableList<String> timePeriodBoxOptions  = FXCollections.observableArrayList("History", "Last 10 games", "Last 25 games");
        ObservableList<String> statTypePeriodBoxOptions = FXCollections.observableArrayList("Kills", "Deaths", "Assists", "Healing", "KDA");
        timePeriodComboBox.getItems().addAll(timePeriodBoxOptions);
        statisticTypeComboBox.getItems().addAll(statTypePeriodBoxOptions);
    }
}
