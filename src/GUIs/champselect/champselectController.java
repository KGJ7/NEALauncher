package GUIs.champselect;

import GUIs.preclient.loginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class champselectController {

    @FXML
    private Button fighterButton;
    @FXML
    private Button tankButton;
    @FXML
    private Button assassinButton;
    @FXML
    private Button marksmanButton;
    @FXML
    private Button mageButton;
    @FXML
    private Button lockInButton;
    @FXML
    private Label playerDisplayLabel;
    @FXML
    private Label champSelectTitleLabel;
    @FXML
    private Label lockInLabel;

    private String hoveredChamp;

    public void initialize(){
        playerDisplayLabel.setText(loginController.currentUser);
    }

    public void hoverFighter(){
        hoveredChamp = "Fighter";
        playerDisplayLabel.setText(loginController.currentUser + "\n" + hoveredChamp);
    }
    public void hoverTank(){
        hoveredChamp = "Tank";
        playerDisplayLabel.setText(loginController.currentUser + "\n" + hoveredChamp);
    }
    public void hoverAssassin(){
        hoveredChamp = "Assassin";
        playerDisplayLabel.setText(loginController.currentUser + "\n" + hoveredChamp);
    }
    public void hoverMarksman(){
        hoveredChamp = "Marksman";
        playerDisplayLabel.setText(loginController.currentUser +  "\n" + hoveredChamp);
    }
    public void hoverMage(){
        hoveredChamp = "Mage";
        playerDisplayLabel.setText(loginController.currentUser + "\n" + hoveredChamp);
    }

    public void lockIn(){
        if (hoveredChamp == null){
            lockInLabel.setText("Please select a champion.");
        } else
            lockInLabel.setText("Successfully locked in: " + hoveredChamp);
            loadGame();
    }

    public void loadGame(){

    }

}
