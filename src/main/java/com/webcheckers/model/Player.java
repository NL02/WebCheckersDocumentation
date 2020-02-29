package com.webcheckers.model;

public class Player {
    //private final int playerID; // Internal ID constructed by hashing player info
    public final String name;
    public Status status;

    public enum Status{
        OFFLINE, //Player is not online
        SEARCHING, //Player is not in a game, nor looking for a game
        WAITING, //Player has created a new game and is waiting for
        INGAME; //Player is currently in a game or spectating
    }

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     */
    public Player(String username) {
        this.name = username;
        this.status = Status.SEARCHING;
        //playerID = username.hashCode() + password.hashCode();
    }

    //private int getPlayerID() {
    //   return playerID;
    //}

    public String getName() {
        return name;
    }

    public Status getStatus(){
        return status;
    }

    //todo: get password?
    //todo: hash code is causing problems so I commented it out
}
