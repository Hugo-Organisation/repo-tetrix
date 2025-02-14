package neige;

import java.util.Random;

/**
 * Classe qui décrit un flovcon qui tombe et qui se fait
 * porter par le vent.
 * 
 * @author Eric Lallet.
 *
 */
public class Flocon {
	
	/**
	 * tailles des plus gros flocons (largeur en pixel d'une image carrée).
	 */
	public static final int TAILLEMAX = 20;
	
	/**
	 * vitesse maximale pour le descente des flocons.
	 */
	private static final int VITESSEVERTICALEMAX = 5;
	
	/**
	 * position du flocon de neige sur le paysage.
	 */
	
	private Position position;
	
	/**
	 * vitesse de déplacement horizontale.
	 * 1 = descente d'un pixel tous les tics.
	 */
	private int vitesseVerticale;
	
	/**
	 * vitesse de déplacement verticale.
	 * 1 = déplacement d'un pixel vers la droite tous les tics.
	 */
	private int vitesseHorizontale;
	
	/**
	 * proportion de la taille du flocon de neige. 
	 * Nombre qui doit être compris entre 0.1 et 1 qui
	 * représente une proportion de la taille maximale.
	 * Exemple: 0.25 = 1/4 de la taille maximale.
	 */
	private double proportion;

	/**
	 * générateur de nombres aléatoires.
	 */
	Random random;
	
	/**
	 * Constructeur qui inilialise un flocon. Sa taille et sa
	 * vitesse de descente est aléatoire.
	 * @param position
	 *           position d'apparition du flocon.
	 * @param vitesseVent
	 *           vitesse du vent pour le calcul de la
	 *           vitesse horizontale.
	 */
	public Flocon(Position position, int vitesseVent) {
		random  = new Random();
		this.position = position;
		this.fixerVitesseHorizontale(vitesseVent);
		this.vitesseVerticale = 1 + random.nextInt(VITESSEVERTICALEMAX);
		this.proportion = 0.25 + random.nextDouble(0.75);		
	}
	
	/**
	 * Recalcule la vitesse du flocon en fonction du vent.
	 * @param vitesseVent
	 *           vitesse du vent pour le calcul de la
	 *           vitesse horizontale.     
	 */
	public void fixerVitesseHorizontale(int vitesseVent) {
		if (vitesseVent > 0) { 
			this.vitesseHorizontale = random.nextInt(vitesseVent);
		}
		else if (vitesseVent < 0) {
			this.vitesseHorizontale = -random.nextInt(-vitesseVent);
		}
		else {
			this.vitesseHorizontale = 0;
		}
	}
	
	/**
	 * calcul la position du flocon après un tic.
	 */
	public void tomber() {
		this.position.setCoordY(this.position.getCoordY() + this.vitesseVerticale);
		this.position.setCoordX(this.position.getCoordX() + this.vitesseHorizontale);
	}

	public Position getPosition() {
		return position;
	}
	
	

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getTaille() {
		int taille = (int) (proportion * TAILLEMAX);
		if (taille <= 0) {
			taille = 1;
		}
		return taille;
	}	
	
}
