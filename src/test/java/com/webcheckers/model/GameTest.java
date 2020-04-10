package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class GameTest {

    private Game CuT;

    private Player whitePlayer;
    private Player redPlayer;

    @BeforeEach
    public void setup() {
        whitePlayer = mock(Player.class);
        redPlayer = mock(Player.class);
    }

    @Test
    public void testInitialWhiteOnly() {
        CuT = new Game(whitePlayer);

        assertEquals(whitePlayer, CuT.getWhitePlayer());
        assertEquals(Color.RED, CuT.getActiveColor());

        assertNotNull(CuT.getBoard());

        assertNull(CuT.getRedPlayer());
        assertNull(CuT.getActivePlayer());
        assertNull(CuT.isGameOver());
    }

    @Test
    public void testInitialTwoPlayers() {
        CuT = new Game(whitePlayer, redPlayer);

        assertEquals(whitePlayer, CuT.getWhitePlayer());
        assertEquals(redPlayer, CuT.getRedPlayer());
        assertEquals(Color.RED, CuT.getActiveColor());
        assertEquals(redPlayer, CuT.getActivePlayer());

        assertNotNull(CuT.getBoard());

        assertNull(CuT.isGameOver());
    }

    @Test
    public void testAddRedPlayer() {
        CuT = new Game(whitePlayer);
        CuT.addRedPlayer(redPlayer);

        assertEquals(redPlayer, CuT.getRedPlayer());
        assertEquals(redPlayer, CuT.getActivePlayer());
    }

    @Test
    public void testChangeActiveColor() {
        CuT = new Game(whitePlayer, redPlayer);
        CuT.changeActiveColor();

        assertEquals(Color.WHITE, CuT.getActiveColor());
        assertEquals(whitePlayer, CuT.getActivePlayer());

        CuT.changeActiveColor();
        assertEquals(Color.RED, CuT.getActiveColor());
        assertEquals(redPlayer, CuT.getActivePlayer());
    }

    @Test
    public void testGameOver() {
        CuT = new Game(whitePlayer, redPlayer);
        CuT.gameOver("test", redPlayer);

        assertEquals("test", CuT.isGameOver());
        assertEquals(redPlayer, CuT.winner);
    }
}
