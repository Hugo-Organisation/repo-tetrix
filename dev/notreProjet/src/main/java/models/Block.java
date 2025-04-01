package models;

public class Block {
    private String color;
    private int x;
    private int y;

    public Block(int X, int Y, String C){
        x = X;
        y = Y;
        color = C;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
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
