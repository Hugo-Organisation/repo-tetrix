package neige;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Classe avec quelques exemples de tests pour illustrer l'usage de JUnit
 * 
 * @author Eric Lallet.
 *
 */
public class TestNeige {
	/**
	 * réference sur la classe qui est testée (re-créée avant chaque test par
	 * la méthode setup()
	 */
	private Neige underTest;

	@BeforeEach
	public void setup() {
		// re-création d'une nouvelle classe pour chaque test. 
		underTest = new Neige();
	}
	
	@Test
	@DisplayName("Teste qu'il n'y a aucun flocon présent à la création de la classe")
	void testAucunFloconALaCreation() {
		Assertions.assertTrue(underTest.getFlocons().size() == 0, "La liste des flocons est vide à la création de la classe");
	}
	
	@Test
	@DisplayName("Teste l'ajout d'un flocon")
	void testAjoutDUnFlocon() {
		underTest.add(50, 100);
		Assertions.assertTrue(underTest.getFlocons().size() == 1, "La liste des flocons contient 1 flocon après le premier ajout");
		Position position = new Position(50, 100);
		Assertions.assertTrue(underTest.getFlocons().get(0).getPosition().equals(position),
				"Le flocon est bien à sa position initiale");
	}
	
	@Test
	@DisplayName("Teste que tomberFlocon() fait bien tomber les flocons")
	void testTomberFlocons() {
		underTest.add(50, 100);
		underTest.tomberFlocons();
		Assertions.assertTrue(underTest.getFlocons().get(0).getPosition().getCoordY() > 100,
				"Le flocon est bien tombé après l'appel de la méthode tomberFlocons");
	}
	
	@Test
	@DisplayName("Teste que les flocons sont détruits s'ils quittent l'image pas le bas")
	void testFloconDetruit() {
		underTest.add(50, Neige.HAUTEUR); // un flocon sur le point de quitter l'image
		underTest.tomberFlocons();
		Assertions.assertTrue(underTest.getFlocons().size() == 0, 
				"Le flocon est détruit quand il quitte l'image par le bas");
	}
	
	
}
