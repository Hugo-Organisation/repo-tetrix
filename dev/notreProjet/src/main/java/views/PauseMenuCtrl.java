package views;

import java.io.IOException;

import controls.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PauseMenuCtrl {

    @FXML
    private Button quitButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button resumeButton;

    @FXML
    void onClic(MouseEvent event) {
        if (event.getSource().equals(quitButton)){
            try {
                Stage stage = (Stage) quitButton.getScene().getWindow();
                Font.loadFont(getClass().getResourceAsStream("/fonts/LLPIXEL3.ttf"), 14);
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuView.fxml"));
    
                Scene menuScene = new Scene(root);
                stage.setScene(menuScene);
                stage.sizeToScene();
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        if (event.getSource().equals(restartButton)){
            Stage stage = (Stage) restartButton.getScene().getWindow();

            GameController controller = new GameController();
            controller.startGame(stage);
        }
        if (event.getSource().equals(resumeButton)){
            Stage stage = (Stage) resumeButton.getScene().getWindow();
            stage.close();
        }
    }

}
