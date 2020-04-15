package com.webcheckers.ui.board;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;
import com.webcheckers.model.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

/**
 * Test suite for BoardView. Assumes board size is 8x8.
 *
 * @author Wyatt Holcombe
 */
public class BoardViewTest {

    // Component under test
    private BoardView CuT;

    // Test object(s). Due to BoardView's design none can be mocked.
    private Player whitePlayer;
    private Player redPlayer;
    private Game game;

    /**
     * Before each test, set up the test objects.
     */
    @BeforeEach
    public void setup() {
        whitePlayer = new Player("test1");
        redPlayer = new Player("test2");
        game = new Game(whitePlayer, redPlayer);
    }

    /**
     * Test that the board is initialized correctly by the white player.
     */
    @Test
    public void testInitialWhitePlayer() {
        CuT = new BoardView(whitePlayer, game);
        assertNotNull(CuT);
    }

    /**
     * Test that the board is initialized correctly by the red player.
     */
    @Test
    public void testInitialRedPlayer() {
        CuT = new BoardView(redPlayer, game);
        assertNotNull(CuT);
    }

    /**
     * Test that iterator() returns a valid object.
     */
    @Test
    public void testIterator() {
        CuT = new BoardView(whitePlayer, game);
        Iterator<Row> iterator = CuT.iterator();
        assertNotNull(iterator);
    }

    /**
     * Test next() in BoardIterator.
     */
    @Test
    public void testNext() {
        CuT = new BoardView(whitePlayer, game);
        Iterator<Row> iterator = CuT.iterator();

        // The first seven calls should return a valid row
        for (int i = 0; i < 8; i++) {
            assertNotNull(iterator.next());
        }

        // The next call should return null
        assertNull(iterator.next());
    }

    /**
     * Test hasNext() in BoardIterator.
     */
    @Test
    public void testHasNext() {
        CuT = new BoardView(whitePlayer, game);
        Iterator<Row> iterator = CuT.iterator();

        // The first seven calls should return true
        for (int i = 0; i < 8; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }

        // The next call should be false
        assertFalse(iterator.hasNext());
    }
}
