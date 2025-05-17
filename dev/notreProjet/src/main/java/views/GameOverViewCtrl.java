package views;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverViewCtrl {

    @FXML
    private Text scoreText;

    @FXML
    private Button restartButton;

    @FXML
    private Button quitButton;

    @FXML
    void onClick(MouseEvent event) {
        Stage stage = (Stage) restartButton.getScene().getWindow();

        if (event.getSource() == quitButton) {
            stage.close();
        } else if (event.getSource() == restartButton) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/JeuView.fxml"));
                MediaManager.attachClickSoundToAllButtons(root);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setScore(int score) {
        scoreText.setText("Score: " + score);
    }
}
