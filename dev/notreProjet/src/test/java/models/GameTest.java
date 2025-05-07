package models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    void testAnimateParticles() {
    }

    @Test
    void testCheckCollision() {

        // quand ça touche le bas
        SandArea testsandarea = new SandArea(10, 10, 2);
        int newX = 2;
        int newY = 9;

        if (!testsandarea.checkCollision(newX, newY)) {
            fail("newY >= height - squareRatio");
        }

        // quand il y a un bloc déjà présent
        testsandarea.createBlock(4, 4, javafx.scene.paint.Color.RED);
        boolean result = testsandarea.checkCollision(4, 3);
        assertTrue(result, "Collision attendue avec un bloc déjà présent");

        // espace suffisant
        result = testsandarea.checkCollision(1, 1);
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
