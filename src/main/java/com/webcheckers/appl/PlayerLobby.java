package com.webcheckers.appl;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Map;

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

    private Map <String, String> userMap = new HashMap<String, String>();
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

    public boolean saveUser(String username, String password) {
        if( username == null && username.matches("^[a-zA-Z0-9 ]*$")){
            System.out.println("Not alphaNumeric/spaces");
            return false;
        }
        else if (userMap.containsKey(username)) {
            return false;
        }
        else{
            userMap.put(username, password);
            return true;
        }
    }



}
