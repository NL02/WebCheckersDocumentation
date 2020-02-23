package com.webcheckers.model;

public class CheckersPiece {
    private Color color;
    private boolean isKing;

    protected CheckersPiece(Color c) {
        color = c;
        isKing = false;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isKing() {
        return this.isKing;
    }

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
}
