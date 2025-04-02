package controls;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Block;
import models.Game;
import views.Square;

public class GameController {
    private Square square;
    private Pane root;
    private Scene scene;
    private Game model;

    private final int particleSize = 5;
    private final int squareRatio = 20;
    private final int widthRatio = 7;
    private final int heightRatio = 7;

    private final int width = widthRatio*squareRatio*particleSize;
    private final int height = widthRatio*squareRatio*particleSize;
    private final double initialX = (widthRatio/2)*squareRatio*particleSize;
    private final int v = particleSize;
    private double vy = v;
    private double vx = 0;
    private final int FPS = 60;
    private final HashMap<Block,Square> particles = new HashMap<>();

    public GameController() {
    }

    public void startGame(Stage primaryStage) {
        square = new Square(squareRatio*particleSize, Color.BLACK);
        square.setX(initialX);
        root = new Pane(square);
        scene = new Scene(root, width, height);
        model = new Game(widthRatio*squareRatio, heightRatio*squareRatio,squareRatio);

        getCommand();
        updateFrame();

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Demande le focus pour capter les touches du clavier
        root.requestFocus();
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

    private void updateSquare() {
        double newX = square.getX() + vx;
        double newY = square.getY() + vy;
        
        double minX = 0;
        double maxX = scene.getWidth() - squareRatio*particleSize;
        double minY = 0;
        double maxY = scene.getHeight() - squareRatio*particleSize;
        if (model.checkCollision((int)newX/particleSize, (int)newY/particleSize)) {
            model.createParticle((int)square.getX()/particleSize, (int)square.getY()/particleSize);
            square.setX(initialX);
            square.setY(0);
            vx = 0;
            return;
        }
        
        square.setY(Math.max(minY, Math.min(newY, maxY)));
        square.setX(Math.max(minX, Math.min(newX, maxX)));
        model.animateParticles();

        addNewBlocks();
        removeDeletedBlocks();
        updateParticles();
    }

    public void updateFrame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / FPS), event -> {updateSquare();}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}