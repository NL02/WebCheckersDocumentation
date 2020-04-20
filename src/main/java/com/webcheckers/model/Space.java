package com.webcheckers.model;

/**
 * Object representing a Space on a checkers board.
 *
 * @author Wyatt Holcombe
 */
public class Space {

    private int cellIdx;    // Index of this space on the row
    private boolean valid;  // Whether a piece can be moved here
    private Piece piece;    // Piece that occupies this square, if there is one

    /**
     * Constructs a new Space with an initial piece.
     *
     * @param index Index of this space in a row
     * @param valid Whether this space can be moved to
     * @param piece Piece to start here, if applicable
     */
    public Space(int index, boolean valid, Piece piece) {
        this.cellIdx = index;
        this.valid = valid;
        this.piece = piece;
    }

    /**
     * Constructs a new Space without an initial piece.
     *
     * @param index Index of this space in a row
     * @param valid Whether this space can be moved to
     */
    public Space(int index, boolean valid ) {
        this.cellIdx = index;
        this.valid = valid;
        this.piece = null;
    }

    /**
     * Returns this space's index.
     *
     * @return index
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Returns whether this space is valid for pieces to be placed on.
     *
     * @return valid
     */
    public boolean isValid() {
        if (valid) {
            return piece == null;
        }
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

    /**
     * Set a new piece on this space.
     *
     * @param newPiece The piece to place here
     */
    public void setPiece(Piece newPiece) {
        if (piece == null){
            piece = newPiece;
        }
    }

    /**
     * Remove this space's piece.
     */
    public void removePiece() {
        if ( piece != null ) {
            piece = null;
        }
    }

}
