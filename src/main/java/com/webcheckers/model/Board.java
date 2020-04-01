package com.webcheckers.model;

public class Board {
    private static final int ROWS = 8;
    private static final int COLS = 8;

    private Space[][] board; // board representation
    private Color active; // The color of the active player
    private Player activePlayer; // The player about to make a move

    private boolean isJumping = false;
    private boolean isMoving = false;

    public Board(){
        InitializeSpaces();
        PopulateBoard();
    }

    private void InitializeSpaces() {
        board = new Space[ROWS][COLS];

        for ( int row = 0; row < ROWS; row++ ) {
            for ( int col = 0; col < COLS; col++ ) {
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        board[row][col] = new Space(col, true);
                    } else {
                        board[row][col] = new Space(col, false);
                    }
                } else {
                    if (col % 2 == 1) {
                        board[row][col] = new Space(col, true);
                    } else {
                        board[row][col] = new Space(col, false);
                    }
                }
            }
        }
    }

    private void PopulateBoard() {
        for (int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                if(row < 3) {
                    if ( board[row][col].isValid()) {
                        board[row][col].setPiece(new Piece(Color.RED));
                    }
                } else if (row > 4) {
                    if (board[row][col].isValid()) {
                        board[row][col].setPiece(new Piece(Color.WHITE));
                    }
                }
            }
        }
    }

    public Space[][] getBoard() {
        return board;
    }


}
