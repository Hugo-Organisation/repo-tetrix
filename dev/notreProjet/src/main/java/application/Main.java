package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pieces.Square;

public class Main extends Application {

    private final int width_screen = 700;
    private final int height_screen = 700;
    private final int square_size = 100;

    @Override
    public void start(Stage primaryStage) {
        Square square = new Square(square_size, Color.BLACK);
        Pane root = new Pane(square);
        Scene scene = new Scene(root, width_screen, height_screen);


        Command command = new Command(scene, square, root, width_screen, height_screen, square_size);
        command.getCommand();
        command.updateSquare();

        primaryStage.setTitle("Square move");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Demande le focus pour capter les touches du clavier
        root.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
