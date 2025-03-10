package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pieces.Square;

public class Command {
    private final Scene scene;
    private final Square square;
    private final Pane root;
    private final int v = 5;
    private double vy = 0;
    private double vx = 0;
    private final int width_screen;
    private final int height_screen;
    private final int square_size;
    private final int particle_size = 5;
    private final int FPS = 60;
    private final Square[][] particules;

    public Command(Scene scene, Square square, Pane root, int width_screen, int height_screen, int square_size) {
        this.scene = scene;
        this.square = square;
        this.root = root;
        this.width_screen = width_screen;
        this.height_screen = height_screen;
        this.square_size = square_size;
        this.particules = new Square[width_screen / particle_size][height_screen / particle_size];
    }

    public void getCommand() {
        this.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) this.vy = -v;
            if (event.getCode() == KeyCode.DOWN) this.vy = v;
            if (event.getCode() == KeyCode.LEFT) this.vx = -v;
            if (event.getCode() == KeyCode.RIGHT) this.vx = v;
        });

        this.scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) vy = 0;
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) vx = 0;
        });
    }

    public void createParticule(double x, double y) {
        int gridX = (int) (x / particle_size); // Convertir en indices de grille
        int gridY = (int) (y / particle_size);
        
        for (int i = 0; i < square_size / particle_size; i++) {
            for (int j = 0; j < square_size / particle_size; j++) {
                int newX = (int) x + j * particle_size;
                int newY = (int) y + i * particle_size;
    
                Square particle = new Square(particle_size, Color.RED);
                particle.setX(newX);
                particle.setY(newY);
                
                particules[gridY + i][gridX + j] = particle;
    
                root.getChildren().add(particle);
            }
        }
    }

    private void moveParticule(int old_i, int old_j, int new_i, int new_j){
        Square particle = particules[old_i][old_j];
        particules[old_i][old_j] = null;
        particules[new_i][new_j] = particle;

        // Appliquer la nouvelle position
        particle.setX(new_j * particle_size);
        particle.setY(new_i * particle_size);
    
    }

    public void animatedParticule() {
        for (int i = particules.length - 2; i >= 0; i--) {
            List<int[]> toMove = new ArrayList<>();
            for (int j = 0; j < particules[i].length; j++) {
                if (particules[i][j] != null) {
                    // Vérifier d'abord si on peut tomber en bas
                    if (i < particules.length && particules[i + 1][j] == null) {
                        moveParticule(i, j, i + 1, j);
                    } 
                    // Vérifier ensuite si on peut tomber en bas à gauche
                    else if (i > 0 && j > 0 && particules[i + 1][j - 1] == null && particules[i][j - 1] == null ) { 
                        moveParticule(i, j, i + 1, j - 1);
                    } 
                    // Vérifier si on peut tomber en bas à droite
                    else if (i > 0 && j < particules[i].length - 1 && particules[i + 1][j + 1] == null && particules[i][j + 1] == null ) { 
                        moveParticule(i, j, i + 1, j + 1);
                    }
                }
            }
        }
    }

    public boolean checkCollision(double newX, double newY) {
        // Vérifier si le carré atteint le sol
        if (newY >= height_screen - square_size) {
            return true;
        }
        
        // Calculer la zone occupée par le carré en termes d'indices de grille
        int gridStartX = (int)(newX / particle_size);
        int gridEndX = (int)((newX + square_size - 1) / particle_size);
        int gridStartY = (int)(newY / particle_size);
        int gridEndY = (int)((newY + square_size - 1) / particle_size);
        
        // Parcourir la zone pour voir s'il y a une particule déjà présente
        for (int i = gridStartY; i <= gridEndY; i++) {
            for (int j = gridStartX; j <= gridEndX; j++) {
                // Vérifier que les indices restent dans les limites de la grille
                if (i >= 0 && i < particules.length && j >= 0 && j < particules[i].length) {
                    if (particules[i][j] != null) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    
    
    

    public void updateSquare() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0 / FPS), event -> {

            double newX = square.getX() + vx;
            double newY = square.getY() + vy;

            double minX = 0;
            double maxX = width_screen - square_size;
            double minY = 0;
            double maxY = height_screen - square_size;

            if (checkCollision(newX, newY)) {
                createParticule(newX - vx, newY - vy); // A rendre plus propre
                square.setX(0);
                square.setY(0);
                vx = 0;
                vy = 1;
                return;
            }

            animatedParticule();

            square.setX(Math.max(minX, Math.min(newX, maxX)));
            square.setY(Math.max(minY, Math.min(newY, maxY)));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
