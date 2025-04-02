package views;

import controls.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenu extends VBox{
    public MainMenu(Stage primaryStage){
        super();

        Button startButton = new Button("Démarrer le jeu");

        startButton.setOnAction(
            e -> {
            GameController controller = new GameController();
            controller.startGame(primaryStage);
            }
        );

        this.getChildren().add(startButton);
    }
}
