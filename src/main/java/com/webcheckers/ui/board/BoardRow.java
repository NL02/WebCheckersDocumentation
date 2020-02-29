package com.webcheckers.ui.board;

import java.util.Iterator;

public class BoardRow implements Iterable<BoardSquare> {
    private static final int NUM_SQUARES = 8;

    private final BoardSquare[] squares;
    private final int index;

    /**
     * Constructs a new BoardRow and the BoardSquares it contains.
     * @param index Index of this row on the board.
     */
    public BoardRow(int index) {
        squares = new BoardSquare[NUM_SQUARES];
        this.index = index;
        for (int i = 0; i < NUM_SQUARES; i++) {
            squares[i] = new BoardSquare(i,(index - i) % 2 == 0, makePiece(i));
        }
    }
    
    public int getIndex() {
        return this.index;
    }

    /**
     * Returns a piece to be initially placed in a constructed square if it is
     * a valid square in the first/last three rows.
     *
     * @param i Index of the square in the row
     * @return null if ineligible space, otherwise white or red piece
     */
    private CheckersPiece makePiece(int i) {
        // Determine if a square can start with a piece; return null if not
        if ((index - i) % 2 != 0 || (index > 2 && index < 5)) {
            return null;
        }
        // Determine the right color and return a piece of that color
        else {
            return new CheckersPiece(index < 3 ? Color.WHITE : Color.RED);
        }
    }

    public Iterator<BoardSquare> iterator() {
        return new RowIterator(this);
    }

    public class RowIterator implements Iterator<BoardSquare> {
        private int index;
        private BoardSquare[] squares;

        public RowIterator(BoardRow row) {
            this.squares = row.squares;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < squares.length;
        }

        @Override
        public BoardSquare next() {
            index++;
            return squares[index - 1];
        }
    }
}
