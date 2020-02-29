package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;

/**
 * The object to coordinate the state of the Web Application.
 *
 * This class is an example of the GRASP Controller principle.
 *
 * Handles each individual player in the game
 *
 */

public class PlayerServices {

    //
    // Attributes
    //

    private CheckersGame game;
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
     * Get the current game that the player is playing. Create one if a game has not been started.
     *
     * @return CheckersGame
     *    the current game being played
     */
    public synchronized CheckersGame currentGame() {
        if(game == null) {
            game = lobby.getGame();
        }
        return game;
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
