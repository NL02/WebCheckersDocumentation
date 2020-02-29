package com.webcheckers.model;

public class Player {
    //private final int playerID; // Internal ID constructed by hashing player info
    public final String name;
    private boolean status;

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     */
    public Player(String username) {
        this.name = username;
        this.status = true;
        //playerID = username.hashCode() + password.hashCode();
    }

    //private int getPlayerID() {
    //   return playerID;
    //}

    public String getName() {
        return name;
    }

    public boolean getStatus(){
        return status;
    }

    //todo: get password?
    //todo: hash code is causing problems so I commented it out
}
