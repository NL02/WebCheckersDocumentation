package com.webcheckers.model;

/**
 * Represents a move made by a player.
 */
public class Move {
    private final Position start;
    private final Position end;
    private Position midpoint;

    public Move(final Position start, final Position end) {
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public Position getMidpoint() {
        return midpoint;
    }

    public void setMidpoint() {
        this.midpoint = new Position((start.getRow() + end.getRow()) / 2,
                (start.getCell() + end.getCell()) / 2);
    }

}
