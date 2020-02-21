package com.webcheckers.model;

public class Player {
    private final int playerID; // Internal ID constructed by hashing player info
    private final String username;

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     * @param password User's password to log in with
     */
    public Player(String username, String password) {
        this.username = username;
        playerID = username.hashCode() + password.hashCode();
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getUsername() {
        return username;
    }
}
