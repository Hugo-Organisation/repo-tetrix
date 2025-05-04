package views;
import java.net.URL;
import java.util.ResourceBundle;

import controls.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class JeuViewCtrl implements javafx.fxml.Initializable {
    private GameController controller;

    @FXML
    private Pane gamePane;

    @FXML
    private Pane previewPane;

    @FXML
    private Button pauseButton;

        @FXML
    void onClic(MouseEvent event) {
        if (event.getSource().equals(pauseButton)){
            controller.pauseGame();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        javafx.application.Platform.runLater(() -> {
            Stage stage = (Stage) gamePane.getScene().getWindow();
            controller = new GameController(gamePane);
            controller.startGame(stage);
        });
    }
}
