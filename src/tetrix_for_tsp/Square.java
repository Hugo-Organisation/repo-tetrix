package tetrix_for_tsp;
import java.awt.Color;
import java.awt.Graphics2D;

public class Square {

    int height_size = 50;
    int width_size = 50;
    int velocity = 10;
    Color color = Color.WHITE;	
    double x;
    double y = 100;
    int width_screen;
    Boolean success = null;
    
    public Square(int width) {
    	this.width_screen = width;
    	this.x = Math.random()*width;
    }
    
    public void square_update(KeyHandler keyH) { // permet de contenir le carré et de le faire bouger
    	
    	if (keyH.left_pressed) {
            this.x -= this.velocity;
        }
        if (keyH.right_pressed) {
            this.x += this.velocity;
        }
    	
    	this.x = Math.max(0, Math.min(this.x, this.width_screen - this.width_size));

    }

    public void draw(Graphics2D g) { // permet de l'afficher
        g.fillRect((int)x, (int)y, width_size, height_size);
    }

}