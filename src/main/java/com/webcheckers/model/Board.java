package com.webcheckers.model;

import com.webcheckers.util.Message;

import java.util.ArrayList;

public class Board {
    private static final Message VALID_MOVE = Message.info("Move is valid");
    private static final Message OUT_OF_BOUNDS = Message.error("Move is out of bounds");
    private static final Message INVALID_SPACE = Message.error("Cannot move to white space");
    private static final Message SPACE_OCCUPIED = Message.error("A piece occupies this space");
    private static final Message TOO_FAR = Message.error("Move is too far");
    private static final Message ILLEGAL_COMBO = Message.error("Cannot make simple move and jump in same turn");
    private static final Message NOT_DIAGONAL = Message.error("Cannot move that direction");
    private static final Message NO_PIECE = Message.error("No piece to jump");
    private static final Message NOT_KING = Message.error("Only king pieces can move backwards");

    private static final int ROWS = 8;
    private static final int COLS = 8;


    private Space[][] board; // board representation

    private ArrayList<Move> pendingMoves; // A deque of moves that haven't been submitted
    private boolean isJumping = false;
    private boolean isMoving = false;

    public Board() {
        InitializeSpaces();
        PopulateBoard();
        pendingMoves = new ArrayList<>();
    }

    public Message validateMove(Move move) {
        move.setMidpoint();
        int startX = move.getStart().getRow();
        int startY = move.getStart().getCell();
        int midX = move.getMidpoint().getRow();
        int midY = move.getMidpoint().getCell();
        int endX = move.getEnd().getRow();
        int endY = move.getEnd().getCell();

        Piece movedPiece = board[startX][startY].getPiece();

        // Verify move is in-bounds
        if (endX < 0 || endX == ROWS || endY < 0 || endY == COLS) {
            return OUT_OF_BOUNDS;
        }

        // Verify move is in right direction
        if (movedPiece.getType() != Piece.PieceType.KING) {
            if (movedPiece.getColor() == Color.RED && startX > endX
                || movedPiece.getColor() == Color.WHITE && startX < endX) {
                return NOT_KING;
            }
        }

        // Verify space is unoccupied
        if (board[endX][endY].getPiece() != null) {
            return SPACE_OCCUPIED;
        }
        // Verify space is valid for movement
        else if (!board[endX][endY].isValid()) {
            return INVALID_SPACE;
        }
        // Verify move length
        else if (Math.abs(endX - startX) > 2 || Math.abs(endY - startY) > 2) {
            return TOO_FAR;
        }
        // Verify move is diagonal
        else if (startX == endX || startY == endY) {
            return NOT_DIAGONAL;
        }

        // If move is a simple move, verify it's the only move
        if (Math.abs(endX - startX) == 1) {
            if (pendingMoves.size() > 0) {
                return ILLEGAL_COMBO;
            }
        }
        // Otherwise, it's a jump move, so verify there is a piece to jump over
        else {
            if (board[midX][midY].getPiece() == null) {
                return NO_PIECE;
            }
            else {
                isJumping = true;
            }
        }

        // Move is valid, so add it to pendingMoves
        pendingMoves.add(move);
        isMoving = true;
        return VALID_MOVE;
    }

    public Message submitTurn() {
        for (Move move : pendingMoves) {
            executeMove(move);
        }

        pendingMoves.clear();
        return Message.info("Turn submitted.");
    }

    /**
     * Apply a move to the board.
     *
     * @param move Move to be executed
     */
    private void executeMove(Move move) {
        int startX = move.getStart().getRow();
        int startY = move.getStart().getCell();
        int midX = move.getMidpoint().getRow();
        int midY = move.getMidpoint().getCell();
        int endX = move.getEnd().getRow();
        int endY = move.getEnd().getCell();

        Piece movedPiece = board[startX][startY].getPiece();

        // Remove jumped piece
        if (midY != startY && board[midX][midY].getPiece() != null) {
            board[midX][midY].removePiece();
        }

        // Move piece
        board[endX][endY].setPiece(movedPiece);
        board[startX][startY].removePiece();

        // Make piece a king if it reaches the back
        if (endX == 0 || endX == 7) {
            movedPiece.kingMe();
        }
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


    public Space[] getRow(int index) {
        if (index < 0 || index > 7) {
            throw new IllegalArgumentException("Index must be between 0 and 7");
        }
        return board[index];
    }

    public Space[] getRowReversesd( int index ) {
        Space[] row = getRow(index);
        Space[] reversed = new Space[ROWS];
        int indexing = ROWS;
        for ( int i = 0; i < ROWS; i++) {
            indexing--;
            reversed[i] = row[indexing];

        }
            return reversed;
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
