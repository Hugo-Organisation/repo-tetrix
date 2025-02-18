package application;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import pieces.Square;

public class Command {
    private final Scene scene;
    private final Square square;
    private final int v = 10;

    public Command(Scene scene, Square square) {
        this.scene = scene;
        this.square = square;
    }

    public void getCommand() {
        this.scene.setOnKeyPressed(event -> {
            double vx = 0;
            double vy = 0;

            if (event.getCode() == KeyCode.UP) vy = -this.v;
            if (event.getCode() == KeyCode.DOWN) vy = this.v;
            if (event.getCode() == KeyCode.LEFT) vx = -this.v;
            if (event.getCode() == KeyCode.RIGHT) vx = this.v;

            // Mettre à jour directement la position du carré
            square.setTranslateX(square.getTranslateX() + vx);
            square.setTranslateY(square.getTranslateY() + vy);
        });
    }
}
