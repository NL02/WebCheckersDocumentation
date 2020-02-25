package com.webcheckers.model;

public class Player {
    private final int playerID; // Internal ID constructed by hashing player info
    private final String username;
    private final String password;
    private boolean status;

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     * @param password User's password to log in with
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.status = true;
        playerID = username.hashCode() + password.hashCode();
    }

    private int getPlayerID() {
        return playerID;
    }

    private String getUsername() {
        return username;
    }

    private boolean getStatus(){
        return status;
    }

    //todo: get password?
}
