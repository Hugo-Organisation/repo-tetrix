package views;

import controls.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenu extends VBox{
    public MainMenu(Stage primaryStage,int square_size,int width,int height){
        super();

        Button startButton = new Button("Démarrer le jeu");

        startButton.setOnAction(
            e -> {
            GameController controller = new GameController();
            controller.startGame(primaryStage,width,height);
            }
        );

        this.getChildren().add(startButton);
    }
}
