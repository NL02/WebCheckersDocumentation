package com.webcheckers.appl;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Map;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;

/**
 * The object to coordinate the state of the Web Application and keep sitewide statistics.
 *
 * This class is an example of the Pure Fabrication principle.
 *
 * Contains all players that have signed in and are in the system
 *
 * @author
 * @author
 */
public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    //
    // Attributes
    //

    // change Map to Map <username, Player>
    private Map <String, Player> userMap = new HashMap<>();
    private int totalGames = 0;

    //
    // Public methods
    //

    /**
     * Get a new {PlayerServices} object to provide client-specific services to
     * the client who just connected to this application.
     *
     * @return
     *   A new {PlayerServices}
     */
    public PlayerServices newPlayerServices(){
        LOG.fine("New player services instance created.");
        return new PlayerServices(this);
    }

    /**
     * Create a new {CheckersGame} game.
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

    public boolean saveUser(String username, String password) {
        if( username == null && username.matches("^[a-zA-Z0-9 ]*$")){
            System.out.println("Not alphaNumeric/spaces");
            return false;
        }
        else if (userMap.containsKey(username)) {
            return false;
        }
        else{
            Player player = new Player(username, password);
            userMap.put(username, player);
            return true;
        }
    }



}
