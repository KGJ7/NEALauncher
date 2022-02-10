package GUIs.placeholderGame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class placeholderController {

    @FXML
    private Label gameLabel;
    @FXML
    private Label minimapLabel;
    @FXML
    private ImageView gameIV;
    @FXML
    private ImageView mapIV;
    @FXML
    private Button leaveGameButton;

    public void initialize(){
        setGameLabel();
    }
    @FXML
    public void setGameLabel(){
        Image image = new Image("/Images/img.png");
        gameIV = new ImageView(image);
        gameIV.setFitHeight(936);
        gameIV.setFitWidth(1664);
        gameIV.setPreserveRatio(false);
        gameLabel.setGraphic(gameIV);
        mapIV = new ImageView(image);
        mapIV.setFitHeight(300);
        mapIV.setFitWidth(300);
        mapIV.setPreserveRatio(false);
        minimapLabel.setGraphic(mapIV);
    }

    @FXML
    public void leaveGame(){
            try{
                Stage old = (Stage) leaveGameButton.getScene().getWindow();
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
