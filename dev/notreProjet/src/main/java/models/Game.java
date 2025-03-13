package models;

import javafx.scene.paint.Color;
import views.Square;

public class Game {
    private final int widthScreen;
    private final int heightScreen;
    private final int squareSize;
    private final int particleSize = 5;
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
        for (int i = particles.length - 2; i >= 0; i--) {
            for (int j = 0; j < particles[i].length; j++) {
                if (particles[i][j] != null) {
                    if (i < particles.length - 1 && particles[i + 1][j] == null) {
                        moveParticle(i, j, i + 1, j);
                    } else if (j > 0 && i < particles.length - 1 && particles[i + 1][j - 1] == null && particles[i][j - 1] == null) {
                        moveParticle(i, j, i + 1, j - 1);
                    } else if (j < particles[i].length - 1 && i < particles.length - 1 && particles[i + 1][j + 1] == null && particles[i][j + 1] == null) {
                        moveParticle(i, j, i + 1, j + 1);
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