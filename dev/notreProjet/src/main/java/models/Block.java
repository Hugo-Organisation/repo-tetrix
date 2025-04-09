package models;

public class Block {
    private int x;
    private int y;

    public Block(int X, int Y){
        x = X;
        y = Y;
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
