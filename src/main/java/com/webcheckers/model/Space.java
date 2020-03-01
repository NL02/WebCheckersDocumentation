package com.webcheckers.model;

public class Space {

    public enum TYPE {BLACK, WHITE}; //white space, black space

    private int CellIdx;
    private Piece piece;

    public int getCellIdx() {
        return this.CellIdx;
    }
    public boolean isValid() {
//        if (piece.getType() == Piece.TYPE.SINGLE)
        return false;
    }

    public Piece getPiece() {
        return this.piece;
    }
}
