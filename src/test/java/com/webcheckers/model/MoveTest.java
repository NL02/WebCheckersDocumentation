package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Move class.
 *
 * @author May Wu
 */
@Tag("Model-tier")
public class MoveTest {

    /**
     * The component-under-test (CuT)
     */
    private Move CuT;

    @BeforeEach
    public void setup() {
        Position start = new Position(0, 0);
        Position end = new Position(1,1);
        CuT = new Move(start, end);
    }

    @Test
    public void ctor_exists() {
        assertNotNull(CuT);
    }

    @Test
    public void test_isValid_true() {
        assertTrue(CuT.isValid(), "Move is valid");
    }

    @Test
    public void test_isValid_false() {
        Move invalidStart = new Move(new Position(-1, -1), new Position(0, 0));
        assertFalse(invalidStart.isValid(), "Move is not valid");
        Move invalidEnd = new Move(new Position(5, 6), new Position(8, 8));
        assertFalse(invalidEnd.isValid(), "Move is not valid");
    }

    @Test
    public void test_getStart() {
        assertEquals(0, CuT.getStart().getRow(), "Incorrect start position row");
        assertEquals(0, CuT.getStart().getCell(), "Incorrect start position cell");
    }

    @Test
    public void test_getEnd() {
        assertEquals(1, CuT.getEnd().getRow(), "Incorrect end position row");
        assertEquals(1, CuT.getEnd().getCell(), "Incorrect end position cell");
    }


    @Test
    public void test_getMidpoint() {
        CuT.setMidpoint();
        assertEquals(0, CuT.getMidpoint().getRow(), "Incorrect midpoint position row");
        assertEquals(0, CuT.getMidpoint().getCell(), "Incorrect midpoint position cell");
    }


}
