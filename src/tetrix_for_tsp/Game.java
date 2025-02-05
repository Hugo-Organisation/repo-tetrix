package tetrix_for_tsp;
import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L; //pour retirer un erreur serializable
	int width_screen = 800;
	int height_screen = 800; 
	double FPS = 60;
	
	Thread gameThread;
	Square square = new Square(width_screen);
	KeyHandler keyH = new KeyHandler();
	
	public Game() {
		this.setPreferredSize(new Dimension(width_screen, height_screen));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(keyH);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this); //créer le Thread
		gameThread.start(); //start le Thread
	}
	
	public void run() {
		
		double drawIntervalle = 1000000000/FPS; //temps en nano sec en java
		double nextDrawTime = System.nanoTime() + drawIntervalle; //temps t0 + intervalle
		
		while (gameThread != null) {
			square.square_update(keyH);
			repaint();
			
			
			////////////////////////////////////////
			//  ne pas faire attention, c'est juste une histoire d'FPS
			try {
				double remainingTime = nextDrawTime - System.nanoTime(); 
				remainingTime = remainingTime / 1000000; 
				if (remainingTime < 0) { 
					remainingTime = 0; 
				}
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawIntervalle;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			///////////////////////////////////////
		}
	}
	
	public void paintComponent(Graphics g) { // affiche les éléments à l'écran
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(square.color);
		square.draw(g2);
	}
}
