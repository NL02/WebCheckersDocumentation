package com.webcheckers.model;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Unit test for Board class
 *
 *  @author May Wu
 */
@Tag("Model-tier")
public class BoardTest {

    /**
     * The component-under-test (CuT)
     */

    private Board CuT;

    @BeforeEach
    public void setup() {
        CuT = new Board();
    }

    /**
     * Test that Board is successfully set up
     * Includes test for InitializeSpaces
     * and PopulateBoard
     */
    @Test
    public void test_setup() {
        assertNotNull(CuT);
    }

    /**
     * Tests that color is successfully changed
     */
    @Test
    public void test_changeActiveColor() {
        assertEquals(Color.RED, Board.activeColor);
        CuT.changeActiveColor();
        assertEquals(Color.WHITE, Board.activeColor);
    }

    /**
     * Test that when active color is red, can make valid move
     */
    @Test
    public void test_validateMove_red() {
        Move redMove = new Move(new Position(5, 0), new Position(4, 1));
        assertEquals(Board.VALID_MOVE, CuT.validateMove(redMove));

    }

    /**
     * Test that when active color is red, can make valid move
     */
    @Test
    public void test_validateMove_white() {
        CuT.changeActiveColor();
        Move whiteMove = new Move(new Position(5, 0), new Position(4, 1));
        assertEquals(Board.VALID_MOVE, CuT.validateMove(whiteMove));

    }

    /**
     * Test that an OUT_OF_BOUNDS message is returned when move is out of bounds
     */
    @Test
    public void test_validateMove_bounds() {
        Move y_end = new Move(new Position(1, 7), new Position(0, 8));
        assertEquals(Board.OUT_OF_BOUNDS, CuT.validateMove(y_end));

        Move x_end = new Move(new Position(7, 1), new Position(8, 0));
        assertEquals(Board.OUT_OF_BOUNDS, CuT.validateMove(x_end));
    }


    /**
     * Test that a piece cannot jump an empty piece
     */
    @Test
    public void test_validateMove_simpleJump_noPiece() {
        Move move = new Move(new Position(5, 0), new Position(3, 2));
        assertEquals(Board.NO_PIECE, CuT.validateMove(move));
    }

    /**
     * Test that a piece cannot jump your own piece
     */
    @Test
    public void test_validateMove_simpleJump_sameColor() {
        Move red1 = new Move(new Position(5, 2), new Position(4, 3));
        CuT.validateMove(red1);
        CuT.submitTurn();
        Move white1 = new Move(new Position(5, 4), new Position(4, 3));
        CuT.validateMove(white1);
        CuT.submitTurn();
        Move red2 = new Move(new Position(5, 4), new Position(3, 2));
        assertEquals(Board.OWN_PIECE, CuT.validateMove(red2));
    }

    /**
     * Test that a piece cannot make King moves (WHITE)
     */
    @Test
    public void test_validateMove_notKing_white() {
        Move red1 = new Move(new Position(5, 0), new Position(4, 1));
        CuT.validateMove(red1);
        CuT.submitTurn();
        Move white1 = new Move(new Position(5, 4), new Position(4, 5));
        CuT.validateMove(white1);
        CuT.submitTurn();
        Move red2 = new Move(new Position(5, 2), new Position(4, 3));
        CuT.validateMove(red2);
        CuT.submitTurn();
        Move white_invalid = new Move(new Position(4, 5), new Position(5, 4));
        assertEquals(Board.NOT_KING, CuT.validateMove(white_invalid));
    }

    /**
     * Test that a piece cannot make King moves (RED)
     */
    @Test
    public void test_validateMove_notKing_red() {
        Move red1 = new Move(new Position(5, 2), new Position(4, 3));
        CuT.validateMove(red1);
        CuT.submitTurn();
        Move white1 = new Move(new Position(5, 4), new Position(4, 3));
        CuT.validateMove(white1);
        CuT.submitTurn();
        Move red2 = new Move(new Position(4, 3), new Position(5, 2));
        assertEquals(Board.NOT_KING, CuT.validateMove(red2));
    }

    /**
     * Tests that submit turn for valid move works
     */
    @Test
    public void test_submitTurn_simpleMove() {
        Move move = new Move(new Position(5, 0), new Position(4, 1));
        // validates move, should return true
        CuT.validateMove(move);
        assertEquals(Message.info("Turn submitted.").getText(), CuT.submitTurn().getText());
    }


    @Test
    public void test_backupMove() {
        // makes sure that you cannot back up when moves are made
        assertEquals(Board.NO_MOVES, CuT.backupMove());
        // makes sure that the move can successfully back up
        Move move = new Move(new Position(5, 0), new Position(4, 1));
        CuT.validateMove(move);
        assertEquals(Board.BACKUP_SUCCESSFUL, CuT.backupMove());
    }


    @Test
    public void test_executeMove() {
        Move move = new Move(new Position(5, 0), new Position(4, 1));
    }


    @Test
    public void test_toString() {
        assertNull(CuT.toString());
    }


}
