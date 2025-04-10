package controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

    private List<Block> getAdjacentSameColorBlocks(Block block, Color color, Set<Block> particles) {
        List<Block> neighbors = new ArrayList<>();
        int x = (int) block.getX();
        int y = (int) block.getY();
    
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
    
        for (int[] direction : directions) {
            int nx = x + direction[0];
            int ny = y + direction[1];
            Block neighbor = new Block(nx, ny, color);
    
            // Vérification si le voisin existe dans particles
            if (particles.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        }
    
        return neighbors;
    }
    
    private void checkIfWeShouldDeleteColor() {
        Set<Block> visited = new HashSet<>();
        Set<Block> blocksToDelete = new HashSet<>();
        
        int minX = 0; // bord gauche
        int maxX = width - 1; // bord droit
    
        Set<Block> particleKeys = new HashSet<>(particles.keySet());
    
        for (Block block : particles.keySet()) {
            if (visited.contains(block)) continue;
    
            Color color = block.getColor();
            List<Block> cluster = new ArrayList<>();
        
            boolean touchesLeft = false;
            boolean touchesRight = false;
        
            // BFS
            Queue<Block> queue = new LinkedList<>();
            queue.add(block);
            visited.add(block);
        
            while (!queue.isEmpty()) {
                Block current = queue.poll();
                cluster.add(current);
        
                if (current.getX() <= minX) {
                    touchesLeft = true;
                }
                if (current.getX() >= maxX) {
                    touchesRight = true;
                }

                for (Block neighbor : getAdjacentSameColorBlocks(current, color, particleKeys)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        
            // Si le cluster touche à la fois le bord gauche et droit
            if (touchesLeft && touchesRight) {
                blocksToDelete.addAll(cluster);
            }
        }
        
        // Supprimer les blocs
        for (Block block : blocksToDelete) {
            System.out.println("Suppression de la particule à (" + block.getX() + ", " + block.getY() + ") avec couleur " + block.getColor());
        }
    }
    
    


    private void updateSquare() {

        checkIfWeShouldDeleteColor();

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