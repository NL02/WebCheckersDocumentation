package com.webcheckers.model;

public class CheckersBoard {
    private BoardSquare[][] board;

    public CheckersBoard() {
        this.populateBoard();
    }

    private void populateBoard() {
        board = new BoardSquare[8][8];
        Color squareColor = Color.WHITE; // Square at (0, 0) is always white

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new BoardSquare(squareColor);
                squareColor = squareColor == Color.WHITE ? Color.RED : Color.WHITE;
            }
        }
    }
}
