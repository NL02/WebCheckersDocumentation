package com.webcheckers.model;

import java.util.Iterator;

/**
 * View component for board rows, containing iterable squares. Temporarily holding model-tier information until model-
 * tier board classes are added.
 *
 * @author Wyatt Holcombe
 */
public class Row implements Iterable<Square> {

    private static final int NUM_SQUARES = 8; // Number of squares per row

    private final Square[] squares;    // Array of squares
    private final int index;                // Index of this row on the board

    /**
     * Constructs a new BoardRow and the BoardSquares it contains.
     * @param index Index of this row on the board
     * @param playerColor Color of pieces the player controls; those pieces
     *                    will be rendered in the bottom 3 rows
     */
    public Row(int index, Color playerColor) {
        squares = new Square[NUM_SQUARES];
        this.index = index;

        // Construct squares; second expression determines square validity
        for (int i = 0; i < NUM_SQUARES; i++) {
            squares[i] = new Square(i,(index - i) % 2 != 0, makePiece(i, playerColor));
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
     * Returns a piece to be initially placed in a constructed square if it is
     * a valid square in the first/last three rows.
     *
     * @param i Index of the square in the row
     * @return null if ineligible space, otherwise white or red CheckersPiece
     */
    private Piece makePiece(int i, Color playerColor) {
        // Determine if a square can start with a piece; return null if not
        if ((index - i) % 2 == 0 || (index > 2 && index < 5)) {
            return null;
        }
        // Determine the right color and return a piece of that color
        else {
            return new Piece(index < 3 ? (playerColor == Color.WHITE ? Color.RED : Color.WHITE) : playerColor);
        }
    }

    /**
     * Creates an iterator over this row's squares.
     *
     * @return Iterator over squares
     */
    public Iterator<Square> iterator() {
        return new RowIterator(this);
    }

    /**
     * Iterator that iterates over a BoardRow's squares.
     */
    public class RowIterator implements Iterator<Square> {

        private int index;              // Current index
        private Square[] squares;  // Array of squares from row

        /**
         * Constructs a new RowIterator.
         *
         * @param row BoardRow to iterate over
         */
        public RowIterator(Row row) {
            this.squares = row.squares;
            this.index = 0;
        }

        /**
         * Determines if there are squares left to iterate over.
         *
         * @return true if squares left, else false
         */
        @Override
        public boolean hasNext() {
            return index < squares.length;
        }

        /**
         * Returns the next square.
         *
         * @return Next BoardSquare
         */
        @Override
        public Square next() {
            if (hasNext()) {
                index++;
                return squares[index - 1];
            }
            return null;
        }
    }
}
