package controls;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.Block;
import models.Game;
import views.Square;

public class GameController {
    private final Scene scene;
    private final Square square;
    private final Pane root;
    private final Game model;
    private final int v = 3;
    private double vy = v;
    private double vx = 0;
    private final int FPS = 60;
    private Square[][] particles;

    public GameController(Scene scene, Square square, Pane root, Game model) {
        this.scene = scene;
        this.square = square;
        this.root = root;
        this.model = model;
        particles = new Square[(int)scene.getWidth()/v][(int)scene.getHeight()/v];
    }

    public void getCommand() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) vy = 0;
            if (event.getCode() == KeyCode.DOWN) vy = 2*v;
            if (event.getCode() == KeyCode.LEFT) vx = -v;
            if (event.getCode() == KeyCode.RIGHT) vx = v;
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) vy = v;
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) vx = 0;
        });
    }

    private void addNewBlocks(){
        for(Block block : model.getNewBlocks()){
            int i = block.getX();
            int j = block.getY();
            particles[i][j] = new Square(v, Color.RED, block);
        }
    }

    private void removeDeletedBlocks(){
        for(Block block : model.getNewBlocks()){
            int i = block.getX();
            int j = block.getY();
            particles[i][j] = null;
        }
    }

    private void _updateSquare(){
        for (int i = particles.length - 2; i >= 0; i--) {
            for (int j = 0; j < particles[i].length; j++){
                Square particle = particles[i][j];
                Block block = particle.getRef();
                particle.setX(block.getX());
                particle.setY(block.getY());
                particles[block.getX()][block.getY()] = particle;
                if(i != block.getX() || j!= block.getY()){
                    particles[i][j] = null;
                }
            }
        }
    }
    
    private void updateSquare() {
        double newX = square.getX() + vx;
        double newY = square.getY() + vy;
    
        double minX = 0;
        double maxX = model.getWidth() - model.getSquareSize();
        double minY = 0;
        double maxY = model.getHeight() - model.getSquareSize();
    
        if (model.checkCollision(newX, newY)) {
            model.createParticle(square.getX(), square.getY());
            for (Square[] row : model.getParticles()) {
                for (Square particle : row) {
                    if (particle != null && !root.getChildren().contains(particle)) {
                        root.getChildren().add(particle);
                    }
                }
            }
            square.setX(0);
            square.setY(0);
            vx = 0;
            return;
        }
        model.animateParticles();
        square.setX(Math.max(minX, Math.min(newX, maxX)));
        square.setY(Math.max(minY, Math.min(newY, maxY)));
    }

    public void updateFrame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / FPS), event -> {updateSquare();}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}