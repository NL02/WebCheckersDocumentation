package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the Positions model
 *
 * @author Nelson Liang nl3514
 */
@Tag("Model-tier")
public class PositionTest {

    /**
     * The component-under-test (CuT)
     */
    private Position CuT;
    private Position CuTF; //False position

    @BeforeEach
    public void testSetup() {
        CuT = new Position(1,1);
        CuTF = new Position( -1, -1);

    }
    /**
     * Verify a non-null position
     */
    @Test
    public void validPosition() {
        assertNotNull(CuT);
    }

    /**
     * Verify that two positions are equal to each other
     */
    @Test
    public void samePosition() {
        assertEquals(CuT,CuT);
    }
    /**
     *  Verify that two positions arn't equal to eachother
     */
    @Test
    public void diffPosition() {
        assertNotEquals(CuT,CuTF);
    }
    /**
     * Verify that the isvalid method works accordingly
     */
    @Test
    public void invalidPosition() {
        assertTrue(CuT.isValid());
        assertFalse(CuTF.isValid());
    }





}
