package com.webcheckers.ui.board;

public class BoardSquare {
    private int cellIdx;
    private boolean valid;
    private CheckersPiece piece;

    protected BoardSquare(int index, boolean valid, CheckersPiece piece) {
        this.cellIdx = index;
        this.valid = valid;
        this.piece = piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public boolean isValid() {
        return valid;
    }

    public CheckersPiece getPiece() {
        return this.piece;
    }

}
