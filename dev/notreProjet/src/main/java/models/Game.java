package models;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class Game {
    private final int width;
    private final int height;
    private final int squareRatio;
    private final Block[][] particles;
    private final ArrayList<Block> newBlocks;
    private final ArrayList<Block> deletedBlocks;
    private final ArrayList<Block> movedBlocks;

    public Game(int width, int height, int squareRatio) {
        this.width = width;
        this.height = height;
        this.squareRatio = squareRatio;
        this.particles = new Block[width][height];
        newBlocks = new ArrayList<>();
        deletedBlocks = new ArrayList<>();
        movedBlocks = new ArrayList<>();
    }

    public ArrayList<Block> getNewBlocks(){
        return newBlocks;
    }

    public ArrayList<Block> getDeletedBlocks(){
        return deletedBlocks;
    }

    public ArrayList<Block> getMovedBlocks(){
        return movedBlocks;
    }

    public boolean checkCollision(int newX, int newY) {
        if (newY >= height - squareRatio) return true;
        int gridStartX = newX;
        int gridEndX = newX + squareRatio - 1;
        int gridStartY = newY;
        int gridEndY = newY + squareRatio - 1;

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
    public void createParticle(int x, int y) {
        for (int i = 0; i < squareRatio; i++) {
            for (int j = 0; j < squareRatio; j++) {
                Block particle = new Block(x + j, y + i, Color.RED);
                particles[y + i][x + j] = particle;
                newBlocks.add(particle);
            }
        }
    }

    public void animateParticles() {
        Random random = new Random();
        for (int i = particles.length - 1; i >= 0; i--) {
            double moveLeftOrRight = random.nextDouble();
            int start = moveLeftOrRight < 0.5 ? 0 : particles[i].length - 1;
            int end = moveLeftOrRight < 0.5 ? particles[i].length : -1;
            int step = moveLeftOrRight < 0.5 ? 1 : -1;
            for (int j = start; j != end; j += step) {
                if (particles[i][j] != null) {
                    moveLeftOrRight = random.nextDouble();
                    int balanceLeftAndRight = -1;
                    boolean borderSideCondition1 = j < particles[i].length - 1;
                    boolean borderSideCondition2 = j > 0;
                    if(moveLeftOrRight<0.5){ 
                        balanceLeftAndRight = 1;
                        borderSideCondition1 = j > 0;
                        borderSideCondition2 = j < particles[i].length - 1;
                    }

                    if (i < particles.length - 1 && particles[i + 1][j] == null) {
                        // Si on peut tomber juste en dessous alors on à une proba d'aller sur le coté
                        double sideMoveProba = random.nextDouble();
                        double seuil = 0.2;
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
        Block particle = particles[oldI][oldJ];
        particles[oldI][oldJ] = null;
        particles[newI][newJ] = particle;
        particle.setXY(newJ,newI);
        movedBlocks.add(particle);
    }

    public Block[][] getParticles() {
        return particles;
    }
}