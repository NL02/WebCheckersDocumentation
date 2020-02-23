package com.webcheckers.ui.board;

import java.util.Iterator;

public class BoardRow implements Iterable<BoardSquare> {
    private static final int NUM_SQUARES = 8;

    private final BoardSquare[] squares;
    private final int rowIndex;


    public BoardRow(int index) {
        squares = new BoardSquare[NUM_SQUARES];
        this.rowIndex = index;
    }

    public int getIndex() {
        return this.rowIndex;
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
