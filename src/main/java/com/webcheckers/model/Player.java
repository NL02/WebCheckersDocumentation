package com.webcheckers.model;

public class Player {
    //private final int playerID; // Internal ID constructed by hashing player info
    private final String username;
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

    private String getUsername() {
        return username;
    }

    private boolean getStatus(){
        return status;
    }

    //todo: get password?
    //todo: hash code is causing problems so I commented it out
}
