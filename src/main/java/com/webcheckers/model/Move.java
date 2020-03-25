package com.webcheckers.model;

/**
 * Represents a move made by a player.
 */
public class Move {
    private Position start;
    private Position end;

    public Move(Position start, Position end) {
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
}
