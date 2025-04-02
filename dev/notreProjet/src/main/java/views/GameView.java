package views;

import controls.GameController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Game;

public class GameView{

    public GameView(double spacing){
        
    }

    public static void startGame(Stage primaryStage,int square_size,int width,int height) {
        Square square = new Square(square_size, Color.BLACK);
        Pane root = new Pane(square);
        Scene gameScene = new Scene(root, width, height);

        Game game = new Game(width, height, square_size);
        GameController command = new GameController(gameScene, square, root, game);
        command.getCommand();
        command.updateFrame();

        primaryStage.setTitle("Game");
        primaryStage.setScene(gameScene);
        primaryStage.show();
        
        // Demande le focus pour capter les touches du clavier
        root.requestFocus();
    }
}
