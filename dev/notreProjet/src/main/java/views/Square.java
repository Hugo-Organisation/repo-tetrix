package views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Block;

public class Square extends Rectangle {
    Block ref;

    public Square(double size, Color color, Block block) {
        super(size, size, color);
        ref = block;
    }

    public Block getRef(){
        return ref;
    }
}