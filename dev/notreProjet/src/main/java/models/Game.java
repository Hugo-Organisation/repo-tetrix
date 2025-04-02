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

    /**
     * Il s'agit du constructeur.
     * @param width Il s'agit de la largeur de la scene en unité "particule"
     * @param height Il s'agit de la hauteur de la scene en unité "particule"
     * @param squareRatio Il s'agit de la taille du carré qui tombe en continue en unité de particule
     */
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

    /**
     * Cet fonction teste les collisions pour un seul carré de tétromino. 
     * Sachant que les tétrominos sont un arangement de 4 carré.
     * @param newX Il s'agit de la coordonné X du carré en unité "particule"
     * @param newY Il s'agit de la coordonné Y du carré en unité "particule"
     * @return Retourn True s'il y à collision et False sinon
     */
    public boolean checkCollision(int newX, int newY) {
        if (newY >= height - squareRatio) return true;
        int gridStartX = newX;
        int gridEndX = newX + squareRatio - 1;
        int gridStartY = newY;
        int gridEndY = newY + squareRatio - 1;

        for (int i = gridStartY; i <= gridEndY; i++) {
            for (int j = gridStartX; j <= gridEndX; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height) {
                    if (particles[i][j] != null) return true;
                }
            }
        }
        return false;
    }

    /**
     * Cette fonction transforme le carré en particule.
     * @param x Il s'agit de la coordonné X du carré en unité "particule"
     * @param y Il s'agit de la coordonné Y du carré en unité "particule"
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

    /**
     * Cette fonction fait évoluer l'ensemble des particules d'une frame pour qu'elle respecte la physique choisit.
     */
    public void animateParticles() {
        Random random = new Random();
        for (int i = width - 1; i >= 0; i--) {
            //Le parcours de la cette boucle se fait soit dans le sens des j croissants soit dans le sens des j décroissant.
            //C'est à ça que sert cette section.
            //début de section
            double moveLeftOrRight = random.nextDouble();
            int start = moveLeftOrRight < 0.5 ? 0 : height - 1;
            int end = moveLeftOrRight < 0.5 ? height : -1;
            int step = moveLeftOrRight < 0.5 ? 1 : -1;
            for (int j = start; j != end; j += step) {
            //fin de section
                if (particles[i][j] != null) { // Test de la présence d'une particule
                    //La section suivante sert à choisir si on tombe en priorité à droite ou à gauche lorsqu'on à le choix
                    //début de section
                    moveLeftOrRight = random.nextDouble();
                    int balanceLeftAndRight = -1;
                    boolean borderSideCondition1 = j < height - 1;
                    boolean borderSideCondition2 = j > 0;
                    if(moveLeftOrRight<0.5){ 
                        balanceLeftAndRight = 1;
                        borderSideCondition1 = j > 0;
                        borderSideCondition2 = j < height - 1;
                    }
                    //fin de section

                    //Cette section traite le cas où une particule est en chute libre.
                    //Si la particule peut tomber alors elle tombe.
                    //Mais elle à une probabilité de ce déplacer sur le coté (comme s'il y avait du vent).
                    //début de section
                    if (i < width - 1 && particles[i + 1][j] == null) {
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
                    //fin de section

                    //Cette section gère les empillements de particule.
                    //Si un particule à un écart de plus de 1 unité de haut à gauche ou à droite alors elle tombe du coté en question
                    //début de section
                    else if (borderSideCondition1 && i < width - 1 && particles[i + 1][j - 1*balanceLeftAndRight] == null && particles[i][j - 1*balanceLeftAndRight] == null) {
                        moveParticle(i, j, i, j - 1*balanceLeftAndRight);
                    }
                    else if (borderSideCondition2 && i < width - 1 && particles[i + 1][j + 1*balanceLeftAndRight] == null && particles[i][j + 1*balanceLeftAndRight] == null) {
                        moveParticle(i, j, i, j + 1*balanceLeftAndRight);
                    }
                    //fin de section
                }
            }
        }
    }

    /**
     * Cette fonction déplace une particule dans la matrice de la postion (oldJ,oldI) à (newJ,newI).
     * Elle met à jours ses coordonnées interne pour pouvoir être affiché par le moteur graphique.
     * @param oldI Coordonné Y de la particule en unité de "particule"
     * @param oldJ Coordonné X de la particule en unité de "particule"
     * @param newI Coordonné Y d'arrivé de la particule en unité de "particule"
     * @param newJ Coordonné X d'arrivé de la particule en unité de "particule"
     */
    private void moveParticle(int oldI, int oldJ, int newI, int newJ) {
        Block particle = particles[oldI][oldJ];
        particles[oldI][oldJ] = null;
        particles[newI][newJ] = particle;
        particle.setXY(newJ,newI);
        movedBlocks.add(particle);
    }
}