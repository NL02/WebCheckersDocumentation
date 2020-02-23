package com.webcheckers.model;

public class BoardSquare {
    private Color color;
    private CheckersPiece piece;

    protected BoardSquare(Color c, CheckersPiece p) {
        this.color = c;
        this.piece = p;
    }

    protected BoardSquare(Color c) {
        this(c, null);
    }

    public boolean hasPiece() {
        return this.piece != null;
    }

    public void placePiece(CheckersPiece p) {
        if (this.piece != null) {
            this.removePiece();
        }
        this.piece = p;
    }

    public CheckersPiece removePiece() {
        CheckersPiece copy = piece.duplicate();
        this.piece = null;
        return copy;
    }
}
