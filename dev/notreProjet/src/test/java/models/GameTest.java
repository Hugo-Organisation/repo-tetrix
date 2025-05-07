package models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.fail;

public class GameTest {
    @Test
    void testAnimateParticles() {

    }

    @Test
    void testCheckCollision() {

        // quand ca touche le bas
        SandArea testsandarea = new SandArea(10,10,2);
        int newX = 2;
        int newY = 9;
        if (testsandarea.checkCollision(int newX,int newY) == false) {
        fail("newY>= height - squareRatio");
        }

        // quand il y a un block deja présent
        // Simule un bloc présent à (4,4)
        testsandarea.createBlock(4, 4, javafx.scene.paint.Color.RED);
        // Essaye de poser une forme par-dessus : collision attendue
        boolean result = testsandarea2.checkCollision(4, 3);
        assertTrue(result, "Collision attendue avec un bloc déjà présent");
//
        //espace suffisant
        boolean result = testsandarea.checkCollision(1, 1);
        assertFalse(result, "Pas de collision attendue : zone vide");
    }

    @Test
    void testCreateParticle() {

    }

    @Test
    void testGetHeight() {

    }

    @Test
    void testGetParticles() {

    }

    @Test
    void testGetSquareSize() {

    }

    @Test
    void testGetWidth() {

    }
}
