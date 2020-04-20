package com.webcheckers.model;

import java.util.Iterator;

/**
 * View component for board rows, containing iterable squares. Temporarily holding model-tier information until model-
 * tier board classes are added.
 *
 * @author Wyatt Holcombe
 */
public class Row implements Iterable<Space> {

    private static final int NUM_SQUARES = 8; // Number of squares per row

    private final Space[] spaces;    // Array of squares
    private final int index;         // Index of this row on the board

    /**
     * Constructs a new BoardRow and the BoardSquares it contains.
     * @param index Index of this row on the board
     */
    public Row(int index, Space[] spaces) {
        this.spaces = new Space[NUM_SQUARES];
        this.index = index;
        for( int i = 0; i < NUM_SQUARES; i++ ) {
            this.spaces[i] = new Space(i, spaces[i].isValid(),spaces[i].getPiece());
        }
    }

    /**
     * Returns the index of this row on the board.
     *
     * @return index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Creates an iterator over this row's squares.
     *
     * @return Iterator over squares
     */
    public Iterator<Space> iterator() {
        return new RowIterator(this);
    }

    /**
     * Iterator that iterates over a BoardRow's squares.
     */
    public static class RowIterator implements Iterator<Space> {

        private int index;              // Current index
        private final Space[] spaces;  // Array of squares from row

        /**
         * Constructs a new RowIterator.
         *
         * @param row BoardRow to iterate over
         */
        public RowIterator(Row row) {
            this.spaces = row.spaces;
            this.index = 0;
        }

        /**
         * Determines if there are squares left to iterate over.
         *
         * @return true if squares left, else false
         */
        @Override
        public boolean hasNext() {
            return index < spaces.length;
        }

        /**
         * Returns the next square.
         *
         * @return Next BoardSquare
         */
        @Override
        public Space next() {
            if (hasNext()) {
                index++;
                return spaces[index - 1];
            }
            return null;
        }
    }
}
