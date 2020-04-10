package com.webcheckers.appl;

import com.webcheckers.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Test suite for the Player class.
 *
 * @author Wyatt Holcombe
 */
@Tag("Application Tier")
public class PlayerTest {

    // Component under test
    private Player CuT;

    // Mocked object(s)
    private Game game;

    @BeforeEach
    public void setup() {
        game = mock(Game.class);
        CuT = new Player("test");
    }

    /**
     * Test that each field in Player is initialized as expected.
     */
    @Test
    public void testInitialization() {
        // Correct name
        assertEquals("test", CuT.getName());
        // Correct initial status
        assertEquals(Player.Status.SEARCHING, CuT.status);
        // No game should be found
        assertNull(CuT.getGame());
    }

    /**
     * Test that the equals() method in Player behaves correctly.
     */
    @Test
    public void testEquals() {
        // Same instance should be equal
        assertEquals(CuT, CuT);
        // Same name should be equal
        assertEquals(CuT, new Player("test"));
        // Different name should not be equal
        assertNotEquals(CuT, new Player("test2"));
        // Different type should not be equal
        assertNotEquals(CuT, new Object());
    }

    /**
     * Test game start and end methods.
     */
    @Test
    public void testGame() {
        // Start game
        CuT.startGame(game);

        // CuT should have a game
        assertNotNull(CuT.getGame());
        // Game should be same instance
        assertEquals(game, CuT.getGame());

        // End game
        CuT.endGame(true);
        // Game should now be null
        assertNull(CuT.getGame());
        // Player status should be SEARCHING
        assertEquals(Player.Status.SEARCHING, CuT.status);

        // End game when spectating
        CuT.status = Player.Status.SPECTATING;
        CuT.endGame(false);
        // Game should be null
        assertNull(CuT.getGame());
        // Status should be SEARCHING
        assertEquals(Player.Status.SEARCHING, CuT.status);
    }

    /**
     * Test endSession method.
     */
    @Test
    public void testEndSession() {
        CuT.startGame(game);
        CuT.endSession();

        // Status should be OFFLINE
        assertEquals(Player.Status.OFFLINE, CuT.status);
        // Game should be null
        assertNull(CuT.getGame());
    }
}
