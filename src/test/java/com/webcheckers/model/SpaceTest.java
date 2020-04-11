package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Space class.
 *
 * @author May Wu
 */
@Tag("Model-tier")
public class SpaceTest {

    /**
     * The component-under-test (CuT)
     */
    private Space CuT;

    @BeforeEach
    public void setup() {
        CuT = new Space(0, true);
    }

    /**
     * Tests that constructing a Space is not null
     */
    @Test
    public void ctor_noPiece() {
        assertNotNull(CuT);
    }

    /**
     * Tests that you return the correct cell index
     */
    @Test
    public void test_getCellIdx() {
        assertEquals(0, CuT.getCellIdx(), "Cell ID is 0");
    }

    /**
     * Tests that the space is a valid space
     */
    @Test
    public void test_isValid() {
        assertTrue(CuT.isValid(), "Space is valid");
    }

    /**
     * Tests that you can return the right piece in space
     */
    @Test
    public void test_getPiece() {
        assertNull(CuT.getPiece(), "Space should not have piece");
    }

    /**
     * Tests that piece exists when set
     */
    @Test
    public void test_setPiece() {
        Piece temp = new Piece(Color.RED);
        CuT.setPiece(temp);
        assertNotNull(CuT.getPiece(), "Space should have piece");
    }

    /**
     * Tests that piece does not exist when removed
     */
    @Test
    public void test_removePiece() {
        // set a piece
        Piece temp = new Piece(Color.RED);
        CuT.setPiece(temp);

        // removes the piece
        CuT.removePiece();
        assertNull(CuT.getPiece(), "Piece should be removed");
    }

}
