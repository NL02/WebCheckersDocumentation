package com.webcheckers.model;

/**
 * Represents the location of an element on the board.
 */
public class Position {
    private int row;    // Row index
    private int cell;   // Column/cell index

    /**
     * Constructs a new Position object.
     *
     * @param row Row index this position represents
     * @param cell Column/Cell index this position represents
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Checks if Position is valid
     * @return true if Position is a valid space, false otherwise
     */
    public boolean isValid() {
        return (0 < this.row && this.row < 7) && (0 < this.cell && this.cell < 7);
    }

    /**
     * @return Position row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return Position Cell
     */
    public int getCell() {
        return cell;
    }

}
