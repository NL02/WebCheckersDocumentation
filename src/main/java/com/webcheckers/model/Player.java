package com.webcheckers.model;

public class Player {
    //private final int playerID; // Internal ID constructed by hashing player info
    public final String name;
    public Status status;
    public String gameURL;
    public int playerID;

    /**
     * Status - enums for the current state of the player
     */
    public enum Status{
        OFFLINE, //Player is not online
        SEARCHING, //Player is not in a game, nor looking for a game
        WAITING, //Player has created a new game and is waiting for
        INGAME //Player is currently in a game or spectating
    }

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     */
    public Player(String username) {
        this.name = username;
        this.status = Status.SEARCHING;
        playerID = username.hashCode();
        this.gameURL = "/game" + playerID;
    }

    //private int getPlayerID() {
    //   return playerID;
    //}

    /**
     * getName returns the name of the player instance
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * getStatus returns the status of the player
     * @return (enum) Status
     */
    public Status getStatus(){
        return status;
    }

    /**
     * checks if the given object is equal (has the name username) as this player instance
     *
     * @param obj Object to check
     * @return boolean based on whether or not obj has the same username as this instance of player
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        else if (!(obj instanceof Player)){
            return false;
        }
        final Player that = (Player) obj;
        return this.name == that.getName();
    }
}
