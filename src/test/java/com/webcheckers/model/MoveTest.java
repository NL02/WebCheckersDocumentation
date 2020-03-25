package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Move class
 *
 * @author May Wu mw4815
 */
@Tag("Model-tier")
public class MoveTest {

    private Move CuT;

    @BeforeEach
    public void setup() {
        CuT = new Move(new Position(0,0), new Position(1,1));
    }

    /**
     * Creates valid argument move
     */
    @Test
    public void ctor_withArg() {
        assertNotNull(CuT, "Move is not created");
    }

    /**
     * Gets correct start position
     */
    @Test
    public void test_getStart() {
        Position start = CuT.getStart();
        assertEquals(0, start.getRow(), "Incorrect start row");
        assertEquals(0, start.getCell(), "Incorrect start cell");
    }

    /**
     * Gets correct end position
     */
    @Test
    public void test_getEnd() {
        Position end = CuT.getEnd();
        assertEquals(1, end.getRow(), "Incorrect end row");
        assertEquals(1, end.getCell(), "Incorrect end cell");

    }

    /**
     * Checks if move is a valid move
     */
    @Test
    public void test_isValid() {
        assertTrue(CuT.isValid());
        Move invalid = new Move(new Position(7,7), new Position(8,8));
        assertFalse(invalid.isValid(), "Position (8,8) does not exist");
        invalid = new Move(new Position(0,0), new Position(0,1));
        assertFalse(invalid.isValid(), "Move is not diagonal");
    }
}
