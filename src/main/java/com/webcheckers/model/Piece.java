package com.webcheckers.model;

/**
 * Object representing a checkers piece.
 *
 * @author Wyatt Holcombe
 */
public class Piece {
    private Color color;    // Color of this piece, red or white
    private PieceType type; // Type of piece, single or king


    // Enum representing the two types of pieces.
    public enum PieceType {
        SINGLE,
        KING
    }

    /**
     * Constructs a new CheckersPiece.
     *
     * @param c Color of piece
     */
    public Piece(Color c) {
        this.color = c;
        this.type = PieceType.SINGLE;
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

    /**
     * Changes this piece to a King.
     */
    public void kingMe(){
        this.type = PieceType.KING;
    }
}
