package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import pieces.Square;

public class Command {
    private final Scene scene;
    private final Square square;
    private final int v = 2;
    private double vy = 1;
    private double vx = 0;
    private final int width_screen;
    private final int height_screen;
    private final int square_size;
    private final int FPS = 60;

    public Command(Scene scene, Square square, int width_screen, int height_screen, int square_size) {
        this.scene = scene;
        this.square = square;
        this.width_screen = width_screen;
        this.height_screen = height_screen;
        this.square_size = square_size;
    }

    public void getCommand() {
        this.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) this.vy = -v;
            if (event.getCode() == KeyCode.DOWN) this.vy = v;
            if (event.getCode() == KeyCode.LEFT) this.vx = -v;
            if (event.getCode() == KeyCode.RIGHT) this.vx = v;
        });

        this.scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) vy = 1;
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) vx = 0;
        });
    }

    public void updateSquare() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0/FPS), event -> {
            double newX = square.getTranslateX() + vx;
            double newY = square.getTranslateY() + vy;

            // Vérification des bords ((0,0) est au centre de l'écran)
            double minX = -width_screen / 2 + square_size / 2;
            double maxX = width_screen / 2 - square_size / 2;
            double minY = -height_screen / 2 + square_size / 2;
            double maxY = height_screen / 2 - square_size / 2;

            square.setTranslateX(Math.max(minX, Math.min(newX, maxX)));
            square.setTranslateY(Math.max(minY, Math.min(newY, maxY)));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
