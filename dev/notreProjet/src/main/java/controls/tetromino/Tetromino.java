package controls.tetromino;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.Form;
import models.Game;

public class Tetromino {
    private final Square[] squares;
    private Form form;
    private Color couleur;
    private double x,y;
    private final int squareSize;
    
    public Tetromino(int squareSize){
        this.squareSize= squareSize;
        x = 0;
        y = 0;
        squares = new Square[4];
        for(int i = 0; i<4; i++){
            squares[i] = new Square(squareSize, Color.BLACK);
        }
        form = Form.getFormeAleatoire(squareSize);
    }

    private void updateSquarePosition(){
        int[][] mat = form.getMatrice();
        int k = 0;
        for(int i=0; i<mat.length;i++){
            for(int j=0; j<mat.length;j++){
                if(mat[i][j] == 1){
                    squares[k].setX((j*squareSize + x));
                    squares[k].setY((i*squareSize + y));
                    k++;
                }
            }
        }
    }

    public boolean checkFormCollision(Game model,double vx, double vy, int particleSize){
        for(int k=0; k<4;k++){
            double X = squares[k].getX() + vx;
            double Y = squares[k].getY() + vy;
            if(model.checkCollision((int)X/particleSize, (int)Y/particleSize)){
                return true;
            }
        }
        return false;
    }

    public void createParticleFromForm(Game model, int particleSize){
        for(int k=0; k<4;k++){
            double X = squares[k].getX();
            double Y = squares[k].getY();
            model.createParticle((int)X/particleSize, (int)Y/particleSize);
        }
    }

    public void rotation(){
        form.rotation();
        updateSquarePosition();
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double X, double width, double height){
        double minX = 0;
        double maxX = width - form.getXSpace()*squareSize;
        x = Math.max(minX, Math.min(X, maxX));
        updateSquarePosition();
    }

    public void setY(double Y,double width, double height){
        double minY = 0;
        double maxY = height - form.getYSpace()*squareSize;
        y = Math.max(minY, Math.min(Y, maxY));
        updateSquarePosition();
    }

    public void addToRoot(Pane root){
        for(int i = 0; i<4;i++){
            root.getChildren().add(squares[i]);
        }
    }

    public void reset(){
        form = Form.getFormeAleatoire(squareSize);
        updateSquarePosition();
    }
}
