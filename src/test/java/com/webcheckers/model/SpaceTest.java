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

    @Test
    public void ctor_noPiece() {
        assertNotNull(CuT);
    }

    @Test
    public void test_getCellIdx() {
        assertEquals(0, CuT.getCellIdx(), "Cell ID is 0");
    }

    @Test
    public void test_isValid() {
        assertTrue(CuT.isValid(), "Space is valid");
    }

    @Test
    public void test_getPiece() {
        assertNull(CuT.getPiece(), "Space should not have piece");
    }

    @Test
    public void test_setPiece() {
        Piece temp = new Piece(Color.RED);
        CuT.setPiece(temp);
        assertNotNull(CuT.getPiece(), "Space should have piece");
    }

    @Test
    public void test_removePiece() {
        CuT.removePiece();
        assertNull(CuT.getPiece(), "Piece should be removed");
    }

}
