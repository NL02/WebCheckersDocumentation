package com.webcheckers.appl;

import com.webcheckers.model.Game;

/**
 * The object to coordinate the state of the Web Application.
 * This class is an example of the GRASP Controller principle.
 * Handles each individual player in the game
 */

public class PlayerServices {

    //
    // Attributes
    //

    private Game game;
    private final PlayerLobby lobby;

    //
    // Public Methods
    //

    /**
     * Construct a new {PlayerServices} but wait for the player to want to start a game
     *
     * @param lobby
     *      the {PlayerLobby} that has site wide responsibilities
     */
    PlayerServices(PlayerLobby lobby){
        game = null;
        this.lobby = lobby;
    }


    /**
     * Indicates that the player is finished with this game.
     */
    public void finishedGame() {
        game = null;
    }

    /**
     * Cleanup the @Linkplain{PlayerServices} object when the session expires.
     * The only cleanup will be to remove the game.
     */
    public void endSession() { game = null; }



}
