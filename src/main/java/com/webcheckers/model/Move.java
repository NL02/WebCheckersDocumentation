package com.webcheckers.model;

/**
 * Represents a move made by a player.
 */
public class Move {
    private final Position start; // Start position of the move
    private final Position end;   // End position of the move
    private Position midpoint;    // Midpoint of the move

    /**
     * Constructs a new Move.
     *
     * @param start Initial position
     * @param end Final position
     */
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
        return Math.abs(start.getCell() - end.getCell()) == 1;
    }

    /**
     * Returns this move's starting point.
     *
     * @return Starting position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Returns this move's end point.
     *
     * @return End position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Returns this move's midpoint.
     *
     * @return Midpoint position
     */
    public Position getMidpoint() {
        return midpoint;
    }

    /**
     * Constructs this move's midpoint.
     *
     * This is required in order to use midpoints because some parts of the
     * application bypass the above constructors.
     */
    public void setMidpoint() {
        this.midpoint = new Position((start.getRow() + end.getRow()) / 2,
                (start.getCell() + end.getCell()) / 2);
    }

}
