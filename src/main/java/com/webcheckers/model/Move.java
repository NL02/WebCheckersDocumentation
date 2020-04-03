package com.webcheckers.model;

/**
 * Represents a move made by a player.
 */
public class Move {
    private final Position start;
    private final Position end;
    private final Position midpoint;

    public Move(final Position start, final Position end) {
        this.start = start;
        this.end = end;
        this.midpoint = new Position((start.getRow() + end.getRow() / 2),
                (start.getCell() + end.getCell() / 2));
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

}
