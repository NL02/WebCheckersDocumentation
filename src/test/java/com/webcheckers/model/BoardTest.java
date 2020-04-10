package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

/**
 *  Unit test for Board class
 *
 *  Author:
 */
@Tag("Model-tier")
public class BoardTest {

    /**
     * The component-under-test (CuT)
     */

    private Board CuT;

    // attributes holding mock objects
    private Space[][] anotherBoard;

    @BeforeEach
    public void setup() {
        CuT = new Board();
    }

    @Test
    public void initColor() {
        assertEquals(Color.RED,CuT.getActiveColor());
    }

    @Test
    public void changeColor() {
        CuT.changeActiveColor();
        assertEquals(Color.WHITE,CuT.getActiveColor());
    }

    @Test
    public void gettingBoard() {
        anotherBoard = mock(Space[][].class);
        assertSame(anotherBoard, CuT.getBoard());
    }



}
