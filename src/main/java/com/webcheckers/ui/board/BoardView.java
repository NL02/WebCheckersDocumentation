package com.webcheckers.ui.board;

import java.util.Iterator;

public class BoardView implements Iterable<BoardRow> {
    private static final int NUM_ROWS = 8;

    private final BoardRow[] rows;

    public BoardView() {
        rows = new BoardRow[NUM_ROWS];
    }

    public Iterator<BoardRow> iterator() {
        return new BoardIterator(this);
    }


    public class BoardIterator implements Iterator<BoardRow> {
        private int index;
        private final BoardRow[] rows;

        public BoardIterator(BoardView board) {
            this.rows = board.rows;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < rows.length;
        }

        @Override
        public BoardRow next() {
            index++;
            return rows[index - 1];
        }

    }
}
