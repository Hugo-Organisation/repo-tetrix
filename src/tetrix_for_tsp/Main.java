package tetrix_for_tsp;
import javax.swing.JFrame;

//test matisse 2

public class Main {
	public static void main(String[] args) {
		
		System.setProperty("sun.java2d.opengl", "true");       // évite les lags (bug de latence)
		
		JFrame window = new JFrame();                          // class Jframe pour créer une fenêtre
		Game game = new Game(); 							   // class pour le jeu
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de la fermer
		window.setResizable(false);                            // empêche de resizer
		window.setTitle("TSP TETRIX GAME");					   // nom de la fenêtre
		window.add(game);                                      // ajoute le jeu à la fenêtre
		window.pack();                                         // compile
		window.setLocationRelativeTo(null);                    // centre la fenêtre
		window.setVisible(true);                               // permet de l'afficher
		game.startGameThread();                                // lance la boucle du jeu
		
	}
}