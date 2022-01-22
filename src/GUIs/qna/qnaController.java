package GUIs.qna;

import Functions.JavaMailUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Observable;

public class qnaController {

    @FXML
    private Label QNAHeaderLabel;
    @FXML
    private Label submittingSideNoteLabel;
    @FXML
    private Label backgroundLabel;
    @FXML
    private ComboBox QNATypeComboBox;
    @FXML
    private Button submitButton;
    @FXML
    private TextArea QNABodyTextArea;
    @FXML
    private Button backToNewsButton;

    public void initialize() throws SQLException {
        initializeComboBoxes();
    }

    public void backToNewsTab() {
        try {
            Stage old = (Stage) backToNewsButton.getScene().getWindow();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeComboBoxes() {
        ObservableList<String> QNATypeForCB = FXCollections.observableArrayList("Ingame error", "Client error", "General question");
        QNATypeComboBox.getItems().addAll(QNATypeForCB);
    }
    public boolean sendMail() throws MessagingException {
        JavaMailUtil.sendMail(QNATypeComboBox.getValue().toString(), QNABodyTextArea.getText());
        return true;
    }

    public void sendQNA() throws MessagingException {
        if(checkFieldsEmpty()){
            if(sendMail()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Email sent!");
                alert.setContentText("Your email has been sent to my inbox. T");
                alert.showAndWait().ifPresent((btnType) -> {
                });
            }
            // code redirection to previous page
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Make sure all the fields are populated!");
            alert.showAndWait().ifPresent((btnType) -> {
            });
        }



    }

    public boolean checkFieldsEmpty(){
        // if empty then return true
        if (!QNABodyTextArea.getText().isEmpty()){
            if(!QNATypeComboBox.getSelectionModel().isEmpty()){
                return true;
            }else return false;
        } else return false;
    }
}
