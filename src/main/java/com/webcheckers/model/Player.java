package com.webcheckers.model;

public class Player {
    //private final int playerID; // Internal ID constructed by hashing player info
    private final String username;
    // whether or not the player is active
    private boolean status;

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     */
    public Player(String username) {
        this.username = username;
        this.status = true;
        //playerID = username.hashCode() + password.hashCode();
    }

    //private int getPlayerID() {
    //   return playerID;
    //}

    public String getUsername() {
        return username;
    }

    public boolean getStatus(){
        return status;
    }

    //todo: get password?
    //todo: hash code is causing problems so I commented it out
}
