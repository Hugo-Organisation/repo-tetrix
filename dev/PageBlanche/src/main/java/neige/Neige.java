package neige;

import java.util.LinkedList;
import java.util.List;


/**
 * classe pour décrire un paysage avec des flocons de neiges qui tombent.
 * 
 * @author Eric Lallet.
 *
 */
public class Neige {
	// taille du paysage
	/**
	 * Largeur du paysage.
	 */
	public static final int LARGEUR = 800;
	/**
	 * Hauteur du paysage.
	 */
	public static final int HAUTEUR = 600;

	/**
	 * liste des flocons qui tombe.
	 */
	private List<Flocon> flocons;
	
	/**
	 * vitesse du vent. 
	 * Si > 0 les flocons vont vers la droite.
	 * Si < 0 les flocons vont vers la gauche.  
	 */
	private int vitesseVent;
	
	/**
	 * Constructeur qui démare avec une liste vide et un vent à zéro.
	 */
	public Neige() {
		this.flocons = new LinkedList<Flocon>();
		this.vitesseVent = 0;
	}
	
	/**
	 * ajoute un nouveau flocon en position coordX, coordY
	 * @param coordX
	 * 			coordX du nouveau flocon.
	 * @param coordY
	 * 			coordY du nouveau flocon.
	 */
	public void add(int coordX, int coordY) {
		Position positions = new Position(coordX, coordY);
		this.flocons.add(new Flocon(positions, vitesseVent));
	}
	
	/**
	 * fait tomber les flocons (méthode à appeler à chaque tic
	 * pour que les flocons bougent).
	 */
	public void tomberFlocons() {
		List<Flocon> floconsDisparus = new LinkedList<Flocon>();
		for(Flocon flocon: flocons) {
			flocon.tomber();
			Position position = flocon.getPosition();
			if (position.getCoordY()  > HAUTEUR) {
				// ce flocon quitte l'image pas le bas;
				floconsDisparus.add(flocon);
				continue;
			}
			
			if (flocon.getPosition().getCoordX()  > LARGEUR) {
				// ce flocon quitte l'image par la droite
				// et revient à gauche
				position.setCoordX(- flocon.getTaille() );;
				flocon.setPosition(position);
				continue;
			}
			
			if (flocon.getPosition().getCoordX() + flocon.getTaille()  < 0) {
				// ce flocon quitte l'image par la gauche.
				// et revient à droite
				position.setCoordX(LARGEUR);
				flocon.setPosition(position);
				continue;
			}
			
		}
		// on supprime les flocons qui ont quitté l'image.
		for(Flocon flocon: floconsDisparus) {
			flocons.remove(flocon);
		}
	}

	public List<Flocon> getFlocons() {
		return flocons;
	}
	
	/**
	 * change la vitesse du vent.
	 * @param sensDuVent
	 *            sens du changement.
	 */
	public void changerVitesseDuVent(SensDuVent sensDuVent) {
		switch (sensDuVent) {
		case VERS_LA_DROITE:
			vitesseVent++;
			break;
		case VERS_LA_GAUCHE:
			vitesseVent--;
			break;
		}
		// recalcule la vitesse horizontale de tous les flocons.
		for (Flocon flocon: flocons) {
			flocon.fixerVitesseHorizontale(vitesseVent);
		}
	}
		
}
