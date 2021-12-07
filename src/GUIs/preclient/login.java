package GUIs.preclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class login extends Application {

    public void start(Stage primaryStage) throws Exception{
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("loginFXML.fxml"));
        Scene scene =  new Scene(root, 1664, 936);
        scene.getStylesheets().add(getClass().getResource("/Stylesheets/Login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Log in");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

