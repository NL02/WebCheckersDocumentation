package com.webcheckers.model;

import com.webcheckers.util.Message;

import java.util.ArrayList;

public class Board {
    // Move validation messages
    static final Message VALID_MOVE = Message.info("Move is valid");
    static final Message OUT_OF_BOUNDS = Message.error("Move is out of bounds");
    static final Message INVALID_SPACE = Message.error("Cannot move to white space");
    static final Message SPACE_OCCUPIED = Message.error("A piece occupies this space");
    static final Message TOO_FAR = Message.error("Move is too far");
    static final Message ILLEGAL_COMBO = Message.error("Cannot make simple move and jump in same turn");
    static final Message NOT_DIAGONAL = Message.error("Cannot move that direction");
    static final Message NO_PIECE = Message.error("No piece to jump");
    static final Message NOT_KING = Message.error("Only king pieces can move backwards");
    static final Message OWN_PIECE = Message.error("Can't jump own piece");

    // Submit turn messages
    static final Message JUMP_AVAILABLE = Message.error("A jump is available, so you must make a jump move");

    // Backup move messages
    static final Message BACKUP_SUCCESSFUL = Message.info("Move undone");
    static final Message NO_MOVES = Message.error("No moves to back up");

    static final int ROWS = 8;
    static final int COLS = 8;

    static Color activeColor;

    private Space[][] board; // board representation

    private ArrayList<Move> pendingMoves; // A list of moves that haven't been submitted
    private boolean isJumping = false;
    private boolean isMoving = false;

    private int turnStartX = -1;
    private int turnStartY = -1;

    public Board() {
        InitializeSpaces();
        PopulateBoard(false);
        pendingMoves = new ArrayList<>();
        activeColor = Color.RED;
    }

    public Message validateMove(Move move) {
        int startX = move.getStart().getRow();
        int startY = move.getStart().getCell();
        int endX = move.getEnd().getRow();
        int endY = move.getEnd().getCell();

        if(activeColor == Color.RED){
            startX = 7 - startX;
            startY = 7 - startY;
            endX = 7 - endX;
            endY = 7 -endY;
            Position newStart = new Position(startX, startY);
            Position newEnd = new Position(endX, endY);
            move = new Move(newStart, newEnd);
        }

        if (turnStartX == -1) {
            turnStartX = startX;
            turnStartY = startY;
        }

        move.setMidpoint();

        int midX = move.getMidpoint().getRow();
        int midY = move.getMidpoint().getCell();

        Piece movedPiece = board[turnStartX][turnStartY].getPiece();

        // Verify move is in-bounds
        if (endX < 0 || endX == ROWS || endY < 0 || endY == COLS) {
            return OUT_OF_BOUNDS;
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
            else if (board[midX][midY].getPiece().getColor() == activeColor) {
                return OWN_PIECE;
            }
            else {
                isJumping = true;
            }
        }

        // Verify move is in right direction
        //if (!isJumping) {
            if (movedPiece.getType() != Piece.PieceType.KING) {
                if (movedPiece.getColor() == Color.RED && startX > endX
                        || movedPiece.getColor() == Color.WHITE && startX < endX) {
                    return NOT_KING;
                }
            }
        //}

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

        // Move is valid, so add it to pendingMoves
        pendingMoves.add(move);
        isMoving = true;
        return VALID_MOVE;
    }

    /**
     * Execute a player's moves for the turn.
     *
     * @return Message indicating submission success or failure
     */
    public Message submitTurn() {
        // If it's a simple move, verify no jumps are available
        if (pendingMoves.size() == 1) {
            int startX = pendingMoves.get(0).getStart().getRow();
            int endX = pendingMoves.get(0).getEnd().getRow();

            if (Math.abs(endX - startX) == 1) {
                for (int row = 0; row < ROWS; row++) {
                    for (int cell = 0; cell < COLS; cell++) {
                        // Verify a piece of the active color is at this cell
                        if (board[row][cell].getPiece() != null
                                && board[row][cell].getPiece().getColor() == activeColor) {
                            if (checkJumps(row, cell)) {
                                return JUMP_AVAILABLE;
                            }
                        }
                    }
                }
            }
        }

        for (Move move : pendingMoves) {
            executeMove(move);
        }

        pendingMoves.clear();
        turnStartX = -1;
        turnStartY = -1;
        isMoving = false;
        isJumping = false;
        return Message.info("Turn submitted.");
    }

    /**
     * Undo the most recent move.
     *
     * @return Message indicating backup success or failure
     */
    public Message backupMove() {
        if (pendingMoves.size() > 0) {
            pendingMoves.remove(pendingMoves.size() - 1);
            return BACKUP_SUCCESSFUL;
        }
        return NO_MOVES;
    }

    protected boolean checkCanMove(int row, int col){
        return checkJumps(row, col) || checkSimpleMoves(row, col);
    }

    /**
     * Check to see if any of four potential jump moves from a space are valid.
     * Used when verifying if a jump is available when submitting a turn.
     *
     * @param row Starting row
     * @param cell Starting cell
     * @return true if a jump exists; else false
     */
    private boolean checkJumps(int row, int cell) {
        Piece piece = board[row][cell].getPiece();
        Position start = new Position(row, cell);
        Position northWest = null;
        Position northEast = null;
        Position southWest = null;
        Position southEast = null;

        if (piece.getType() == Piece.PieceType.KING || piece.getColor() == Color.WHITE) {
            if (row > 1 && cell > 1)
                northWest = new Position(row - 2, cell - 2);
            if (row > 1 && cell < 6)
                northEast = new Position(row - 2, cell + 2);
        }
        if (piece.getType() == Piece.PieceType.KING || piece.getColor() == Color.RED) {
            if (row < 6 && cell > 1)
                southWest = new Position(row + 2, cell - 2);
            if (row < 6 && cell < 6)
                southEast = new Position(row + 2, cell + 2);
        }

        Position[] testPositions = new Position[]{northWest, northEast, southWest, southEast};

        for (Position p : testPositions) {
            if (p != null) {
                if (canJump(new Move(start, p))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Verify that the given move is a valid jump.
     * Used when verifying if a jump is available when submitting a turn.
     *
     * @param move Move to verify
     * @return true if jump is possible; else false
     */
    private boolean canJump(Move move) {
        move.setMidpoint();
        int midX = move.getMidpoint().getRow();
        int midY = move.getMidpoint().getCell();

        return board[midX][midY].getPiece() != null && endSpaceOpen(move)
                && board[midX][midY].getPiece().getColor() != activeColor;
    }

    /**
     * Verify that there is a simple move open to this piece.
     *
     * @param row Starting row index
     * @param cell Starting cell index
     * @return true if a move is open; else false
     */
    private boolean checkSimpleMoves(int row, int cell) {
        Piece piece = board[row][cell].getPiece();
        Position start = new Position(row, cell);
        Position northWest = null;
        Position northEast = null;
        Position southWest = null;
        Position southEast = null;

        if (piece.getType() == Piece.PieceType.KING || piece.getColor() == Color.WHITE) {
            if (row > 0 && cell > 0)
                northWest = new Position(row - 1, cell - 1);
            if (row > 0 && cell < 7)
                northEast = new Position(row - 1, cell + 1);
        }
        if (piece.getType() == Piece.PieceType.KING || piece.getColor() == Color.RED) {
            if (row < 7 && cell > 0)
                southWest = new Position(row + 1, cell - 1);
            if (row < 7 && cell < 7)
                southEast = new Position(row + 1, cell + 1);
        }

        Position[] testPositions = new Position[]{northWest, northEast, southWest, southEast};

        for (Position p : testPositions) {
            if (p != null) {
                if (endSpaceOpen(new Move(start, p))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Verify that the end space of this move is not occupied.
     *
     * @param move Move to check
     * @return true if end space has no piece; else false
     */
    private boolean endSpaceOpen(Move move) {
        int endX = move.getEnd().getRow();
        int endY = move.getEnd().getCell();

        return board[endX][endY].getPiece() == null;
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

    /**
     * Function to test end of game by blocked piece
     * @param test
     */
    private void PopulateBoard(boolean test){
        if(test){
            board[0][1].setPiece(new Piece(Color.RED));
            board[2][1].setPiece(new Piece(Color.WHITE));
            board[4][3].setPiece(new Piece(Color.WHITE));
        }
        else{
            PopulateBoard();
        }
    }

    /**
     * Populate board with pieces in checkers format
     */
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

    protected void changeActiveColor(){
        if(activeColor == Color.RED){
            activeColor = Color.WHITE;
        }
        else{
            activeColor = Color.RED;
        }
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

    @Override
    public String toString(){
        return null;
    }

}
