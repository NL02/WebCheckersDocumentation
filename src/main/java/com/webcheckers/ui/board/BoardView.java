package com.webcheckers.ui.board;

import com.webcheckers.model.Row;
import com.webcheckers.model.Color;

import java.util.Iterator;

/**
 * View component for the checkers board that contains iterable rows. Temporarily holding model-tier information until
 * model-tier board classes are added.
 *
 * @author Wyatt Holcombe
 */
public class BoardView implements Iterable<Row> {

    private static final int NUM_ROWS = 8;  // Number of rows the board contains

    private final Row[] rows;          // Array of rows from board

    /**
     * Constructs a new BoardView and its rows.
     *
     * @param playerColor Color of pieces the player controls; those pieces will be rendered in the bottom 3 rows
     */
    public BoardView(Color playerColor) {
        rows = new Row[NUM_ROWS];

        // Construct rows
        for (int i = 0; i < NUM_ROWS; i++) {
            rows[i] = new Row(i, playerColor);
        }
    }

    /**
     * Creates an iterator over the BoardView's rows.
     *
     * @return Iterator over rows
     */
    public Iterator<Row> iterator() {
        return new BoardIterator(this);
    }

    /**
     * Iterator that iterates over BoardView's rows.
     */
    public class BoardIterator implements Iterator<Row> {

        private int index;              // Current index
        private final Row[] rows;  // Rows to iterate over

        /**
         * Constructs a new BoardIterator.
         *
         * @param board BoardView to iterate over
         */
        public BoardIterator(BoardView board) {
            this.rows = board.rows;
            index = 0;
        }

        /**
         * Determines if there are rows left to iterate over
         *
         * @return true if rows left, else false
         */
        @Override
        public boolean hasNext() {
            return index < rows.length;
        }

        /**
         * Returns the next row.
         *
         * @return Next BoardRow
         */
        @Override
        public Row next() {
            if (hasNext()) {
                index++;
                return rows[index - 1];
            }
            return null;
        }

    }
}
