package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Button leaderboardButton;

    @FXML
    private Button startButton;

    @FXML
    void Onclic(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

