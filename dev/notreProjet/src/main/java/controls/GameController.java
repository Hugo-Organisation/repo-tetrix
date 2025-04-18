package controls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import controls.tetromino.Square;
import controls.tetromino.Tetromino;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Block;
import models.SandArea;
import views.PauseMenuCtrl;


public class GameController {
    private Timeline timeline;
    private Parent pauseMenu;
    private boolean pauseToggle = false;

    private Tetromino tetromino;
    private Pane root;
    private Scene scene;
    private SandArea model;

    private final int particleSize = 5;
    private final int squareRatio = 10;
    private final int widthRatio = 10;
    private final int heightRatio = 14;

    private final int width = widthRatio*squareRatio*particleSize;
    private final int height = heightRatio*squareRatio*particleSize;
    private final double initialX = (widthRatio/2)*squareRatio*particleSize;
    private final int v = particleSize;
    private double vy = v;
    private double vx = 0;
    private final int FPS = 60;
    private final HashMap<Block,Square> particles = new HashMap<>();


    public GameController() {
    }

    public void startGame(Stage primaryStage) {
        System.out.println("Game started");
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
            if (event.getCode() == KeyCode.ESCAPE){
                if(pauseToggle){
                    timeline.play();
                    root.getChildren().remove(pauseMenu);
                }
                else{
                    timeline.pause();
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PauseMenuView.fxml"));
                        pauseMenu = loader.load();                    
                        
                        root.getChildren().add(pauseMenu);
                        PauseMenuCtrl controller = loader.getController();
                        pauseMenu.setLayoutY(height / 2 - controller.getHeight()/2);
                        controller.setResumeButton(() -> {
                            timeline.play();
                            root.getChildren().remove(pauseMenu);
                        });
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                pauseToggle = !pauseToggle;

            }
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
        ArrayList<Block> list = model.getDeletedBlocks();
        ArrayList<Block> toDelete = new ArrayList<>();
        for(Block block : list){
            Square particle = particles.get(block);
            root.getChildren().remove(particle);
            particles.remove(block);
            toDelete.add(block);
        }
        for(Block block : toDelete){
            list.remove(block);
        }
    }

    private void updateParticles(){
        ArrayList<Block> list = model.getMovedBlocks();
        ArrayList<Block> toDelete = new ArrayList<>();
        for(Block block : list){
            Square particle = particles.get(block);
            if(particle != null){
                particle.setX(block.getX()*v);
                particle.setY(block.getY()*v);
            }
            toDelete.add(block);
        }
        for(Block block : toDelete){
            list.remove(block);
        }
    }


    private void updateSquare() {

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
        updateParticles();

        model.removeBlocksToDelete();
        removeDeletedBlocks();
    }

    public void updateFrame() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / FPS), event -> {updateSquare();}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}