package tetrix_for_tsp;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // class qui permet de récupérer les touches
	
	public boolean right_pressed, left_pressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
	
		if (code == KeyEvent.VK_RIGHT) {
			right_pressed = true;
		}
		if (code == KeyEvent.VK_LEFT) {
			left_pressed = true;
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_RIGHT) {
			right_pressed = false;
		}
		if (code == KeyEvent.VK_LEFT) {
			left_pressed = false;
		}
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implémentation requise mais vide
    }

}
