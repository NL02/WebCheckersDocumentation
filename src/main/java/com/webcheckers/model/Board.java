package com.webcheckers.model;

import com.webcheckers.util.Message;

import java.util.Deque;

public class Board {
    private static final Message VALID_MOVE = Message.info("Move is valid");
    private static final Message OUT_OF_BOUNDS = Message.error("Move is out of bounds");
    private static final Message INVALID_SPACE = Message.error("Cannot move to white space");
    private static final Message SPACE_OCCUPIED = Message.error("A piece occupies this space");
    private static final Message TOO_FAR = Message.error("Move goes too far without jumping");

    private static final int ROWS = 8;
    private static final int COLS = 8;


    private Space[][] board; // board representation
    private Space[][] pendingBoard;
    private Color active; // The color of the active player
    private Player activePlayer; // The player about to make a move

    private Deque<Move> pendingMoves; // A deque of moves that haven't been submitted
    private boolean isJumping = false;
    private boolean isMoving = false;

    public Board(){
        InitializeSpaces();
        PopulateBoard();
    }

    public Message validateMove(Move move) {
        int startX = move.getStart().getRow();
        int startY = move.getStart().getCell();
        int endX = move.getEnd().getRow();
        int endY = move.getEnd().getCell();

        // Verify in-bounds
        if (endX < 0 || endX == ROWS || endY < 0 || endY == COLS) {
            return OUT_OF_BOUNDS;
        }
        // Verify valid
        else if (!board[endX][endY].isValid()) {
            return INVALID_SPACE;
        }
        // Verify unoccupied
        else if (board[endX][endY].getPiece() != null) {
            return SPACE_OCCUPIED;
        }

        isMoving = true;
        return VALID_MOVE;
    }

    private void InitializeSpaces() {
        board = new Space[ROWS][COLS];

        for ( int row = 0; row < ROWS; row++ ) {
            for ( int col = 0; col < COLS; col++ ) {
                if (row % 2 == 1) {
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

    /**
     * @return the color of the player whose turn it is
     */
    public Color getActive() {
        return this.active;
    }

    /**
     * @return the player whose turn it is
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * @return true if the pending move is a jump
     */
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * @return true if a piece is moving
     */
    public boolean isMoving() {
        return isMoving;
    }


}
