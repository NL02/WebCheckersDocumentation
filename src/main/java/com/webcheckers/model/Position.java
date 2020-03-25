package com.webcheckers.model;

public class Position {
    private int row;
    private int cell;

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Checks if Position is valid
     * @return true if Position is a valid space, false otherwise
     */
    public boolean isValid() {
        if (this.row  < 0 || this.cell < 0 || this.row > 7 || this.cell > 7) {
            return false;
        }
        return true;
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
