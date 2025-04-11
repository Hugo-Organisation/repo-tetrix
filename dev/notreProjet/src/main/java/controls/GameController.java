package controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import controls.tetromino.Square;
import controls.tetromino.Tetromino;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Block;
import models.SandArea;


public class GameController {
    private Tetromino tetromino;
    private Pane root;
    private Scene scene;
    private SandArea model;

    private final int particleSize = 5;
    private final int squareRatio = 10;
    private final int widthRatio = 14;
    private final int heightRatio = 14;

    private final int width = widthRatio*squareRatio*particleSize;
    private final int height = widthRatio*squareRatio*particleSize;
    private final double initialX = (widthRatio/2)*squareRatio*particleSize;
    private final int v = particleSize;
    private double vy = v;
    private double vx = 0;
    private final int FPS = 60;
    private final HashMap<Block,Square> particles = new HashMap<>();
    private final Map<String, Block> positionToBlock = new HashMap<>();


    public GameController() {
    }

    public void startGame(Stage primaryStage) {
        root = new Pane();
        scene = new Scene(root, width, height);
        model = new SandArea(widthRatio*squareRatio, heightRatio*squareRatio,squareRatio);
        
        tetromino = new Tetromino(squareRatio*particleSize);
        tetromino.addToRoot(root);
        tetromino.setX(initialX,width,height);

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
            if (event.getCode() == KeyCode.UP) vy = -v;
            if (event.getCode() == KeyCode.DOWN) vy = v;
            if (event.getCode() == KeyCode.LEFT) vx = -v;
            if (event.getCode() == KeyCode.RIGHT) vx = v;
            if (event.getCode() == KeyCode.SPACE) tetromino.rotation();
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
            Square particle = new Square(v, tetromino.previous_couleur);
            particle.setX(block.getX()*v);
            particle.setY(block.getY()*v);
            particles.put(block, particle);
            positionToBlock.put(block.getX() + "," + block.getY(), block);
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

    
    private void findCluster() {
        Set<Block> visited = new HashSet<>();
        List<List<Block>> allClusters = new ArrayList<>();
    
        // Étape 1 : trouver tous les clusters
        for (Block start : particles.keySet()) {
            if (visited.contains(start)) continue;
    
            Color color = start.getColor();
            List<Block> cluster = new ArrayList<>();
    
            Queue<Block> queue = new LinkedList<>();
            queue.add(start);
            visited.add(start);
    
            while (!queue.isEmpty()) {
                Block current = queue.poll();
                cluster.add(current);
    
                int x = current.getX();
                int y = current.getY();
    
                int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    String key = nx + "," + ny;
    
                    Block neighbor = positionToBlock.get(key);
                    if (neighbor != null && !visited.contains(neighbor) && neighbor.getColor().equals(color)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            System.out.println(cluster.size());
    
            allClusters.add(cluster);
        }
    }
    



    private void updateSquare() {

        findCluster();

        double newX = tetromino.getX() + vx;
        double newY = tetromino.getY() + vy;

        if (tetromino.checkFormCollision(model,0,vy,particleSize)) {
             tetromino.createParticleFromForm(model,particleSize);
             tetromino.setX(initialX,width,height);
             tetromino.setY(0,width,height);
             tetromino.changeColor();
             tetromino.reset();
             vx = 0;
             return;
         }
         if (tetromino.checkFormCollision(model,vx,0,particleSize)) {
             newX = tetromino.getX();
         }

        tetromino.setY(newY,width,height);
        tetromino.setX(newX,width,height);
        model.animateBlocks();

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