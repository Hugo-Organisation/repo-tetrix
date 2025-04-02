package controls;

import java.util.ArrayList;
import java.util.HashMap;

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
    private int currentFrame = 0;
    private final Square square;
    private final Pane root;
    private final Game model;
    private final int v = 5;
    private double vy = v;
    private double vx = 0;
    private final int FPS = 60;
    private final HashMap<Block,Square> particles;

    public GameController(Scene scene, Square square, Pane root, Game model) {
        this.scene = scene;
        this.square = square;
        this.root = root;
        this.model = model;
        particles = new HashMap<>();
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
        ArrayList<Block> list = model.getNewBlocks();
        ArrayList<Block> toDelete = new ArrayList<>();
        for(Block block : list){
            Square particle = new Square(v, Color.RED);
            particle.setX(block.getX()*v);
            particle.setY(block.getY()*v);
            particles.put(block, particle);
            root.getChildren().add(particle);
            toDelete.add(block);
        }
        for(Block block : toDelete){
            list.remove(block);
        }
    }

    private void removeDeletedBlocks(){
        ArrayList<Block> list = model.getNewBlocks();
        ArrayList<Block> toDelete = new ArrayList<>();
        for(Block block : list){
            particles.remove(block);
            toDelete.add(block);
        }
        for(Block block : toDelete){
            list.remove(block);
        }
    }

    private void updateParticles(){
        ArrayList<Block> list = model.getMovedBlocks();
        for(Block block : list){
            Square particle = particles.get(block);
            particle.setX(block.getX()*v);
            particle.setY(block.getY()*v);
        }
    }

    private void updateSquare(int currentFrame) {
        double newX = square.getX() + vx;
        double newY = square.getY() + vy;
        
        double minX = 0;
        double maxX = model.getWidth() - model.getSquareSize();
        double minY = 0;
        double maxY = model.getHeight() - model.getSquareSize();
        if (model.checkCollision(newX, newY)) {
            model.createParticle(square.getX(), square.getY());
            square.setX(0);
            square.setY(0);
            vx = 0;
            return;
        }
        if (currentFrame%1==0) {
            square.setY(Math.max(minY, Math.min(newY, maxY)));
        }
        square.setX(Math.max(minX, Math.min(newX, maxX)));
        model.animateParticles();

        addNewBlocks();
        removeDeletedBlocks();
        updateParticles();
    }

    public void updateFrame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / FPS), event -> {currentFrame++;updateSquare(currentFrame);}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}