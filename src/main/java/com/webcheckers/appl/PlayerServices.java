package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
//todo get enum for the game in CheckersGame
import com.webcheckers.model.CheckersGame.GuessResult;

/**
 * The object to coordinate the state of the Web Application.
 *
 * This class is an example of the GRASP Controller principle.
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

    /**
     * Construct a new {@Linkplain PlayerServices} but wait for the player to want to start a game
     *
     * @param lobby
     *      the {@Link PlayerLobby} that has site wide responsibilities
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
            game = PlayerLobby.getGame();
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
