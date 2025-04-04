package models;

import java.util.Random;

import javafx.scene.paint.Color;

public class Form {
    private int[][] matrice;
    private Color color;
    private int xSpace;
    private int ySpace;
    
    private static final int[][][] FORMES_MATRICES = {
        // I
        {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}},
        // J
        {{1,0,0}, {1,1,1}, {0,0,0}},
        // L
        {{0,0,1}, {1,1,1}, {0,0,0}},
        // O
        {{1,1}, {1,1}},
        // S
        {{0,1,1}, {1,1,0}, {0,0,0}},
        // T
        {{0,1,0}, {1,1,1}, {0,0,0}},
        // Z
        {{1,1,0}, {0,1,1}, {0,0,0}}
    };

    private static final Color[] COULEURS_POSSIBLES = {
        Color.CYAN, Color.BLUE, Color.ORANGE, 
        Color.YELLOW, Color.GREEN, Color.MAGENTA, 
        Color.RED, Color.PINK, Color.WHITE,
        Color.PURPLE, Color.LIME, Color.TEAL
    };
    
    private Form(int[][] matrice) {
        this.matrice = matrice;
        color = getCouleurAleatoire();
        xSpace = calculateXSpace();
        ySpace = calculateYSpace();
    }

    public static Form createForm(int type, Color couleur) {
        if (type < 0 || type >= FORMES_MATRICES.length) {
            throw new IllegalArgumentException("Type de forme invalide: " + type);
        }
        Form form = new Form(FORMES_MATRICES[type]);
        return form;
    }
    
    public static Form getFormeAleatoire(int blockSize) {
        int type = new Random().nextInt(FORMES_MATRICES.length);
        return createForm(type,getCouleurAleatoire());
    }
    
    public static Color getCouleurAleatoire() {
        return COULEURS_POSSIBLES[new Random().nextInt(COULEURS_POSSIBLES.length)];
    }
    
    private int calculateXSpace() {
        int maxCol = 0;
        for (int[] ligne : matrice) {
            for (int col = ligne.length - 1; col >= 0; col--) {
                if (ligne[col] == 1) {
                    if (col + 1 > maxCol) {
                        maxCol = col + 1;
                    }
                    break;
                }
            }
        }
        return maxCol;
    }
    
    private int calculateYSpace() {
        int maxRow = 0;
        for (int row = matrice.length - 1; row >= 0; row--) {
            for (int val : matrice[row]) {
                if (val == 1) {
                    if (row + 1 > maxRow) {
                        maxRow = row + 1;
                    }
                    break;
                }
            }
        }
        return maxRow;
    }
    
    public void rotation() {
        int n = matrice.length;
        int[][] nouvelleMatrice = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nouvelleMatrice[j][n - 1 - i] = matrice[i][j];
            }
        }
        matrice = nouvelleMatrice;
        xSpace = calculateXSpace();
        ySpace = calculateYSpace();
    }

    public int[][] getMatrice(){
        return matrice;
    }

    public int getXSpace(){
        return xSpace; 
    }

    public int getYSpace(){
        return ySpace;
    }
}