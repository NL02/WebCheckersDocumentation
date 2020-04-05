package com.webcheckers.appl;

import com.webcheckers.model.Game;

public class Player {
    //private final int playerID; // Internal ID constructed by hashing player info
    public final String name;
    public Status status;
    public Game game;
    public int playerID;

    private int gamesPlayed = 0;
    private int gamesWon = 0;

    /**
     * Status - enums for the current state of the player
     */
    public enum Status{
        OFFLINE, //Player is not online
        SEARCHING, //Player is not in a game, nor looking for a game
        WAITING, //Player has created a new game and is waiting for
        INGAME, //Player is currently in a game or spectating
        RESIGN //Player has resigned a game
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
    }

    //private int getPlayerID() {
    //   return playerID;
    //}

    public void startGame(Game game){
        this.game = game;
    }

    /**
     * getName returns the name of the player instance
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the game this player is playing.
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * endGame sets the player game to null
     */
    public void endGame(boolean hasWon){
        this.game = null;
        gamesPlayed++;
        if(hasWon){
            gamesWon++;
        }
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
