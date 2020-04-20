package com.webcheckers.appl;

import com.webcheckers.model.Game;

/**
 * The Player class represents one instance of a user or "player" that has
 * logged in to the web server
 *
 * @author Wyatt Holcombe
 */
public class Player {

    /** Player states and essential info */
    public final String name;
    public Status status;
    public Game game;

    /** Player statistics */
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
        ENDGAME, //Player has just finished a gae and has *not* left the game
        SPECTATING //Player is currently spectating a game
    }

    /**
     * Construct a Player and set its fields.
     *
     * @param username User's name for logging in and display
     */
    public Player(String username) {
        this.name = username;
        this.status = Status.SEARCHING;
    }

    /**
     * Adds a game to the Player instance
     * @param game the game this player has joined
     */
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
     * @return current game
     */
    public Game getGame() {
        return game;
    }

    /**
     * endGame sets the player game to null
     */
    public void endGame(boolean hasWon) {
            gamesPlayed++;
            if (hasWon) {
                gamesWon++;
            }
    }

    /**
     * A function that is called when a user closes out of the web page
     * Sets them to offline and ends the game they are in
     */
    public void endSession(){
        this.status = Status.OFFLINE;
        this.game = null;
    }

    /**
     * Returns the number of games the player has participated in
     * @return number of games player
     */
    public int getGamesPlayed(){
        return gamesPlayed;
    }

    /**
     * Returns the number of games this player has won
     * @return number of games won
     */
    public int getGamesWon(){
        return gamesWon;
    }

    /**
     * Calculates and returns this players win percentage
     * @return win percentage
     */
    public float getWinPercent(){
        return  gamesPlayed != 0 ? (float)gamesWon/(float)gamesPlayed * 100 : 0;
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
        return this.name.equals(that.getName());
    }
}
