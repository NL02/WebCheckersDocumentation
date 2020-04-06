package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Player class.
 *
 * @author Wyatt Holcombe
 */
@Tag("Model-Tier")
public class PlayerTest {

    /**
     * Parameters for testing methods
     */
    private final String TEST_NAME = "test";

    /**
     * Component-under-test
     */
    private Player CuT;

    /**
     * Set up CuT before each test
     */
    @BeforeEach
    public void setup() {
        CuT = new Player(TEST_NAME);
    }

    /**
     * Test constructor to see if all fields are properly initialized
     */
    @Test
    public void testConstructor() {
        assertEquals(TEST_NAME, CuT.getName());
        assertEquals(Player.Status.SEARCHING, CuT.status);
        assertNull(CuT.game);
    }

    /**
     * Test equals method
     */
    @Test
    public void testEquals() {
        // Same instance
        assertEquals(CuT, CuT);
        // Different instance, same name
        assertEquals(CuT, new Player(TEST_NAME));
        // Different instance, different name
        assertNotEquals(CuT, new Player("Jim"));
        // Different class
        assertNotEquals(CuT, new Object());
        // Null object
        assertNotEquals(CuT, null);
    }

    /**
     * Test startGame method
     */
    @Test
    public void testStartGame() {
        // Inject mock game into CuT
        CheckersGame game = mock(CheckersGame.class);
        CuT.startGame(game);

        // Analyze results
        assertNotNull(CuT.game);
        assertEquals(CuT.game, game);
    }

}
