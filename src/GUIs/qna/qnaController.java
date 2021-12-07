package GUIs.qna;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void backToNewsTab(){
        try{
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
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
