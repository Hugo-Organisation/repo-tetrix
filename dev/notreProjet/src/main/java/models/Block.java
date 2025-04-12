package models;

import javafx.scene.paint.Color;

public class Block {
    private int x;
    private int y;
    private Color color;

    public Block(int X, int Y, Color current_couleur){
        x = X;
        y = Y;
        this.color = current_couleur;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Color getColor(){
        return color;
    }

    public void setX(int X){
        x = X;
    }

    public void setY(int Y){
        y = Y;
    }

    public void setXY(int X, int Y){
        this.setX(X);
        this.setY(Y);
    }
}
