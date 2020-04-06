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

    /**
     * Checks if Move is valid
     * @return true if Move is a valid space, false otherwise
     */
    public boolean isValid() {
        if(!start.isValid() || !end.isValid()) {
            return false;
        }
        if (Math.abs(start.getRow() - end.getRow()) != 1) {
            return false;
        }
        if (Math.abs(start.getCell() - end.getCell()) != 1) {
            return false;
        }
        return true;
    }

    /**
     * @return Starting position
     */
    public Position getStart() {
        return start;
    }

    /**
     * @return End position
     */
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
