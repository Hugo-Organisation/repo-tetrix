package views;

import java.net.URL;
import java.util.ResourceBundle;

import controls.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuViewCtrl implements javafx.fxml.Initializable {

    @FXML
    private Slider MusicSlider;

    @FXML
    private Text MusicTitle;

    @FXML
    private Text Title;

    @FXML
    private Button aboutButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button leaderboardButton;

    @FXML
    private Button parametresButton;

    @FXML
    private Button startButton;

    @FXML
    void onClic(MouseEvent event) {
        if (event.getSource().equals(startButton)) {
            Stage stage = (Stage) startButton.getScene().getWindow();

            GameController controller = new GameController();
            controller.startGame(stage);
        }
        if (event.getSource().equals(exitButton)){
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.close();
        }
        if (event.getSource().equals(aboutButton)){
            Stage stage = (Stage) aboutButton.getScene().getWindow();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

