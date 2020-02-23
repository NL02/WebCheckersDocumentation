package com.webcheckers.ui.board;

import com.webcheckers.ui.board.BoardSquare;

public class BoardView {
    private BoardSquare[][] board;

    public BoardView() {

    }

    /*
    private void populateBoard() {
        board = new BoardSquare[8][8];
        Color squareColor = Color.WHITE; // Square at (0, 0) is always white

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new BoardSquare(squareColor);
                squareColor = squareColor == Color.WHITE ? Color.RED : Color.WHITE;
            }
        }
    }*/

    /*public Iterator<BoardSquare> iterator() {
        return new BoardIterator(this.board);
    }*/

    /*
    private class BoardIterator implements Iterator<BoardSquare> {
        private int row, column;
        private BoardSquare[][] board;

        public BoardIterator(BoardSquare[][] board) {
            this.board = board;
        }

        public boolean hasNext() {

            return false;
        }

        public BoardSquare next() {
            return board[0][0];
        }
    }

     */

}
