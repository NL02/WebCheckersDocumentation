package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test for Row class.
 *
 * @author May Wu
 */
@Tag("Model-tier")
public class RowTest {

    /**
     * The component-under-test (CuT)
     */
    private Row CuT;
    private static final int NUM_SQUARES = 8;


    @BeforeEach
    public void setup() {
        // creates space array
        Space[] spaces = new Space[NUM_SQUARES];
        for (int i = 0; i < NUM_SQUARES; i++) {
            spaces[i] = new Space(i, true);
        }
        CuT = new Row(0, spaces);
    }

    /**
     * Tests that constructing a Row is not null
     */
    @Test
    public void ctor_exists() {
        assertNotNull(CuT);
    }

    /**
     * Test getting index of row
     */
    @Test
    public void test_getIndex() {
        assertEquals(0, CuT.getIndex(), "Invalid index");
    }

    /**
     * Test iterator exists
     */
    @Test
    public void test_iterator() {
        assertNotNull(CuT.iterator());
    }

    /**
     * Test iterator functions
     */
    @Test
    public void test_hasNext() {
        assertTrue(CuT.iterator().hasNext());
    }

    /**
     * Test iterator functions
     */
    @Test
    public void test_next() {
        assertNotNull(CuT.iterator().next());
    }

}
