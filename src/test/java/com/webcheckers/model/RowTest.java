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
        Space[] spaces = new Space[NUM_SQUARES];
        for (int i = 0; i < NUM_SQUARES; i++) {
            spaces[i] = new Space(i, true);
        }
        CuT = new Row(0, spaces);
    }

    @Test
    public void ctor_exists() {
        assertNotNull(CuT);
    }

    @Test
    public void test_getIndex() {
        assertEquals(0, CuT.getIndex(), "Invalid index");
    }

    @Test
    public void test_iterator() {
        assertNotNull(CuT.iterator());
    }

    @Test
    public void test_hasNext() {
        assertTrue(CuT.iterator().hasNext());
    }

    @Test
    public void test_next() {
        assertNotNull(CuT.iterator().next());
    }

}
