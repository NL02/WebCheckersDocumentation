package com.webcheckers.appl;

import java.util.logging.Logger;

import com.webcheckers.model.CheckersGame;

/**
 * The object to coordinate the state of the Web Application and keep sitewide statistics.
 *
 * This class is an example of the Pure Fabrication principle.
 *
 * @author
 * @author
 */
public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    //
    // Attributes
    //

    private int totalGames = 0;

    //
    // Public methods
    //

    /**
     * Get a new {@Linkplain PlayerServices} object to provide client-specific services to
     * the client who just connected to this application.
     *
     * @return
     *   A new {@Link PlayerServices}
     */
    public PlayerServices newPlayerServices(){
        LOG.fine("New player services instance created.");
        return new PlayerServices(this);
    }

    /**
     * Create a new {@Linkplain CheckersGame} game.
     *
     * @return
     *   A new {@link CheckersGame}
     */
    public CheckersGame getGame(){
        return new CheckersGame();
    }

    /**
     * Collect sitewide statistics when a game is finished.
     */
    public void gameFinished(){
        synchronized(this){
            totalGames++;
        }
    }



}
