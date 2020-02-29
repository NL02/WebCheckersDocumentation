package com.webcheckers.appl;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Map;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;

/**
 * The object to coordinate the state of the Web Application and keep site wide statistics.
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
    private ArrayList<Player> waitingPlayers = new ArrayList<>();
    private static int liveCount = 0;

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

    public boolean saveUser(Player newPlayer) {
        if( newPlayer.getName() == null || !newPlayer.getName().matches("^[a-zA-Z0-9]*$") || newPlayer.getName().matches(".*\\s+.*")){
            System.out.println("Not alphaNumeric/spaces");
            return false;
        }
        if (newPlayer.getName().length() < 1) {
            System.out.println("Not at least one character");
            return false;
        }
        if (userMap.containsKey(newPlayer.getName())) {
            if(userMap.get(newPlayer.getName()).getStatus() == Player.Status.OFFLINE){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            userMap.put(newPlayer.getName(), newPlayer);
            increment();
            return true;
        }
    }

    //todo get list of active users

    public static int getLiveCount() {
        return liveCount;
    }

    public static void increment() {
        liveCount++;
    }

    public static void decrement() {
        liveCount--;
    }

    public ArrayList<Player> getWaitingPlayer() {
        userMap.forEach((s, player) -> {
            if(player.getStatus() == Player.Status.WAITING) {
                if (!waitingPlayers.contains(player)) {
                    waitingPlayers.add(player);
                }
            }
        });
        return waitingPlayers;
    }

    public boolean removeUser(Player player){
        // checking if user exists in array just in case
        if(waitingPlayers.contains(player)){
            waitingPlayers.remove(player);
            return true;
        }
        else{
            return false;
        }
    }

    public Player findPlayer(String username){
        return userMap.getOrDefault(username, null);
    }
}
