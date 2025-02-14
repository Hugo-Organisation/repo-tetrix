package neige;

import java.util.Objects;

/**
 * Classe pour décrire une position dans un repère à deux axes.
 * 
 * @author Eric Lallet.
 *
 */
public class Position {
	
	/**
	 * coordonnée horizontale. 0 = premiere colonne à gauche.
	 */
	private int coordX;
	
	/**
	 * coordonnée verticale. 0 = première ligne en haut.
	 */
	private int coordY;

	/**
	 * Constructeur pour initialiser les 2 attributs de la classe.
	 * @param coordX
	 *            valeur pour l'attribut coordX.
	 * @param coordY
	 *            valeur pour l'attribut coordY.
	 */
	public Position(int coordX, int coordY) {
		super();
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coordX, coordY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return coordX == other.coordX && coordY == other.coordY;
	}

	@Override
	public String toString() {
		return "Position [coordX=" + coordX + ", coordY=" + coordY + "]";
	}
	
	
	

}
