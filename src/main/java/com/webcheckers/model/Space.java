package com.webcheckers.model;

/**
 * View component for squares on the board. Temporarily holding model-tier information until model-tier board classes
 * are added.
 *
 * @author Wyatt Holcombe
 */
public class Space {

    private int cellIdx;         // Index of this square on the row
    private boolean valid;       // Whether a piece can be moved here (true if so, else false)
    private Piece piece; // Piece that occupies this square, if there is one

    /**
     * Constructs a new BoardSquare.
     *
     * @param index Index of this square in a row
     * @param valid Whether a piece can occupy this square
     * @param piece CheckersPiece to start here, if applicable
     */
    public Space(int index, boolean valid, Piece piece) {
        this.cellIdx = index;
        this.valid = valid;
        this.piece = piece;
    }

    public Space(int index, boolean valid ) {
        this.cellIdx = index;
        this.valid = valid;
        this.piece = null;
    }

    /**
     * Returns this square's index.
     *
     * @return index
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Returns whether this square is valid for pieces to be placed on.
     *
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the piece that occupies this square, if one is present.
     *
     * @return piece
     */
    public Piece getPiece() {
        return this.piece;
    }


    public boolean setPiece(Piece newPiece) {
        if (piece != null){
            piece = newPiece;
            return true;
        } else {
            return false;
        }
    }

}
