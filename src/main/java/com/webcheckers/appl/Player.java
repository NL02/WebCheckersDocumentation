package com.webcheckers.appl;

import com.webcheckers.model.Game;

/**
 * Object that represents a WebCheckers player and stores their information.
 */
public class Player {
    /* Public attributes */
    public final String name;   // Player's name
    public Status status;       // Player's status in the application
    public Game game;           // Current game

    /* Private attributes */
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    /**
     * Status - enums for the current state of the player
     */
    public enum Status {
        OFFLINE, //Player is not online
        SEARCHING, //Player is not in a game, nor looking for a game
        WAITING, //Player has created a new game and is waiting for
        INGAME, //Player is currently in a game or spectating
        ENDGAME,
        SPECTATING
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
     * Sets this player's game when they join one.
     *
     * @param game The game that the player joined
     */
    public void startGame(Game game){
        this.game = game;
    }

    /**
     * Returns the name of the player instance.
     *
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
     * Sets the player's game to null.
     */
    public void endGame(boolean hasWon) {
            gamesPlayed++;
            if (hasWon) {
                gamesWon++;
            }
    }

    /**
     * Sets this player's status to OFFLINE.
     */
    public void endSession(){
        this.status = Status.OFFLINE;
        this.game = null;
    }

    /**
     * Returns the number of games this player has played.
     *
     * @return gamesPlayed
     */
    public int getGamesPlayed(){
        return gamesPlayed;
    }

    /**
     * Returns the number of games won by this player.
     *
     * @return gamesWon
     */
    public int getGamesWon(){
        return gamesWon;
    }

    /**
     * Calculates and returns this player's win percentage.
     *
     * @return Float from 0.0 to 100.0%
     */
    public float getWinPercent(){
        return  gamesPlayed != 0 ? (float)gamesWon/(float)gamesPlayed * 100 : 0;
    }

    /**
     * Checks if the given object is equal (has the name username) as this player instance
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
