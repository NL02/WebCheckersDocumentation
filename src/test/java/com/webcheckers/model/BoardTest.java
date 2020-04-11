package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

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



}
