package com.webcheckers.ui.board;

public class CheckersPiece {
    private Color color;
    private PieceType type;

    public CheckersPiece(Color c) {
        color = c;
        type = PieceType.SINGLE;
    }

    public Color getColor() {
        return this.color;
    }

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
