package com.webcheckers.model;

public class Piece {

    public enum TYPE {SINGLE, KING};

    public enum COLOR {RED, WHITE};

    private COLOR color;
    private TYPE type;

    public Piece(TYPE type, COLOR color) {
        this.type = type;
        this.color = color;
    }
    public TYPE getType() {
        return this.type;
    }

    public COLOR getColor() {
        return this.color;
    }
}
