package com.webcheckers.ui.board;

import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Row;

import java.util.Iterator;

/**
 * View component for the checkers board that contains iterable rows.
 *
 * @author Wyatt Holcombe, Nelson Liang
 */
public class BoardView implements Iterable<Row> {

    private static final int NUM_ROWS = 8;  // Number of rows the board contains

    private final Row[] rows;          // Array of rows from board

    /**
     * Constructs a new BoardView and its rows.
     */
    public BoardView(Player me, Game game) {
        rows = new Row[NUM_ROWS];

        Color playerColor;

        if (game.getWhitePlayer().name.equals(me.name)) {
            playerColor = Color.WHITE;
        } else {
            playerColor = Color.RED;
        }
        if (playerColor == Color.WHITE) {
            for (int r = 0; r < 8; r++) {
                rows[r] = new Row( r , game.getBoard().getRow(r));
            }
        }
        if (playerColor == Color.RED) {
            for (int r = 7; r >= 0; r--) {
                rows[r] = new Row( r, game.getBoard().getRowReversed(7-r));
            }
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
    public static class BoardIterator implements Iterator<Row> {

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
