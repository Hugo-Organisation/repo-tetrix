package models;

import java.util.Random;

import javafx.scene.paint.Color;
import views.Square;

public class Game {
    private final int widthScreen;
    private final int heightScreen;
    private final int squareSize;
    private final int particleSize = 3;
    private final Square[][] particles;

    public Game(int widthScreen, int heightScreen, int squareSize) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        this.squareSize = squareSize;
        this.particles = new Square[widthScreen / particleSize][heightScreen / particleSize];
    }

    public int getWidth(){
        return this.widthScreen;
    }

    public int getHeight(){
        return this.heightScreen;
    }

    public int getSquareSize(){
        return this.squareSize;
    }

    public boolean checkCollision(double newX, double newY) {
        if (newY >= heightScreen - squareSize) return true;
        int gridStartX = (int) (newX / particleSize);
        int gridEndX = (int) ((newX + squareSize - 1) / particleSize);
        int gridStartY = (int) (newY / particleSize);
        int gridEndY = (int) ((newY + squareSize - 1) / particleSize);

        for (int i = gridStartY; i <= gridEndY; i++) {
            for (int j = gridStartX; j <= gridEndX; j++) {
                if (i >= 0 && i < particles.length && j >= 0 && j < particles[i].length) {
                    if (particles[i][j] != null) return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param x
     * @param y
     */
    public void createParticle(double x, double y) {
        int gridX = (int) x / particleSize;
        int gridY = (int) y / particleSize;
        for (int i = 0; i < squareSize / particleSize; i++) {
            for (int j = 0; j < squareSize / particleSize; j++) {
                Square particle = new Square(particleSize, Color.RED);
                particle.setX(x + j * particleSize);
                particle.setY(y + i * particleSize);
                particles[gridY + i][gridX + j] = particle;
            }
        }
    }

    public void animateParticles() {
        Random random = new Random();
        for (int i = particles.length - 2; i >= 0; i--) {
            for (int j = 0; j < particles[i].length; j++) {
                if (particles[i][j] != null) {
                    double moveLeftOrRight = random.nextDouble();
                    int balanceLeftAndRight = -1;
                    boolean borderSideCondition1 = j < particles[i].length - 1;
                    boolean borderSideCondition2 = j > 0;
                    if(moveLeftOrRight<0.5){ //Pourquoi il y a du vent vers la gauche ?
                        balanceLeftAndRight = 1;
                        borderSideCondition1 = j > 0;
                        borderSideCondition2 = j < particles[i].length - 1;
                    }

                    if (i < particles.length - 1 && particles[i + 1][j] == null) {
                        // Si on peut tomber juste en dessous alors on à une proba d'aller sur le coté
                        double sideMoveProba = random.nextDouble();
                        double seuil = 0.1;
                        if(sideMoveProba<seuil){
                            if(borderSideCondition1 && particles[i][j - 1*balanceLeftAndRight] == null){
                                moveParticle(i, j, i, j - 1*balanceLeftAndRight);
                            }
                            else if(borderSideCondition2 && particles[i][j + 1*balanceLeftAndRight] == null){
                                moveParticle(i, j, i, j + 1*balanceLeftAndRight);
                            }
                        }
                        else{
                            moveParticle(i, j, i + 1, j);
                        }
                    }
                    else if (borderSideCondition1 && i < particles.length - 1 && particles[i + 1][j - 1*balanceLeftAndRight] == null && particles[i][j - 1*balanceLeftAndRight] == null) {
                        moveParticle(i, j, i, j - 1*balanceLeftAndRight);
                    }
                    else if (borderSideCondition2 && i < particles.length - 1 && particles[i + 1][j + 1*balanceLeftAndRight] == null && particles[i][j + 1*balanceLeftAndRight] == null) {
                        moveParticle(i, j, i, j + 1*balanceLeftAndRight);
                    }
                }
            }
        }
    }

    private void moveParticle(int oldI, int oldJ, int newI, int newJ) {
        Square particle = particles[oldI][oldJ];
        particles[oldI][oldJ] = null;
        particles[newI][newJ] = particle;
        particle.setX(newJ * particleSize);
        particle.setY(newI * particleSize);
    }

    public Square[][] getParticles() {
        return particles;
    }
}