package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Position class.
 *
 * Author: May Wu
 */
@Tag("Model-tier")
public class PositionTest {

    /**
     * The component-under-test (CuT)
     */
    private Position CuT;

    @BeforeEach
    public void setup() {
        CuT = new Position(5, 6);
    }

    @Test
    public void ctor_Arg() {
        assertNotNull(CuT);
    }

    @Test
    public void test_isValid() {
        assertTrue(CuT.isValid(), "CuT is a valid position");
        Position invalidRow = new Position(8, 0);
        assertFalse(invalidRow.isValid(), "Position has an invalid row");
        Position invalidCell = new Position(2, 10);
        assertFalse(invalidCell.isValid(), "Position has an invalid cell");
    }

    @Test
    public void test_getRow() {
        assertEquals(5, CuT.getRow(), "Incorrect Row");
    }

    @Test
    public void test_getCell() {
        assertEquals(6, CuT.getCell(), "Incorrect Cell");
    }
}
