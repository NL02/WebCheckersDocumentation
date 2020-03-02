package com.webcheckers.ui.board;

/**
 * View component for checkers pieces. Temporarily holding model-tier information until model-tier board classes
 * are added.
 *
 * @author Wyatt Holcombe
 */
public class CheckersPiece {

    private Color color;    // Color of this piece, red or white
    private PieceType type; // Type of piece, single or king

    // Enum representing the two possible colors of pieces.
    public enum Color {
        WHITE,
        RED;
    }

    // Enum representing the two types of pieces.
    public enum PieceType {
        SINGLE,
        KING;
    }

    /**
     * Constructs a new CheckersPiece.
     *
     * @param c Color of piece
     */
    public CheckersPiece(Color c) {
        color = c;
        type = PieceType.SINGLE;
    }

    /**
     * Returns the color of this piece.
     *
     * @return RED or WHITE
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the type of this piece.
     *
     * @return SINGLE or KING
     */
    public PieceType getType() {
        return this.type;
    }

    /* Old and not needed for Sprint 1

    public void becomeKing() {
        this.isKing = true;
    }

    public CheckersPiece duplicate() {
        CheckersPiece copy = new CheckersPiece(this.color);
        if (this.isKing) {
            copy.becomeKing();
        }
        return copy;
    }

     */
}
