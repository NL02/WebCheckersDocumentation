package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Piece class.
 *
 * Author: May Wu
 */
@Tag("Model-tier")
public class PieceTest {

    /**
     * The component-under-test (CuT)
     */
    private Piece CuT;

    @BeforeEach
    public void setup() {
        CuT = new Piece(Color.RED);
    }

    @Test
    public void ctor_Arg() {
        assertNotNull(CuT);
    }

    @Test
    public void test_getColor() {
        assertEquals(Color.RED, CuT.getColor(), "Piece is red");
    }

    @Test
    public void test_getType() {
        assertEquals(Piece.PieceType.SINGLE, CuT.getType(), "Piece is single");
    }

    @Test
    public void test_king() {
        CuT.kingMe();
        assertEquals(Piece.PieceType.KING, CuT.getType(), "Piece is king");
    }

}
