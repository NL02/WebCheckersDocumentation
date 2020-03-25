package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Map;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.pageroutes.PostLoginRoute;

/**
 * The object to coordinate the state of the Web Application and keep site wide statistics.
 * This class is an example of the Pure Fabrication principle.
 * Contains all players that have signed in and are in the system
 */
public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    //
    // Attributes
    //

    private ArrayList<Player> waitingPlayers = new ArrayList<>();
    private static int liveCount = 0;
    private ArrayList<Player> onlinePlayers = new ArrayList<>();

    private Map <String, Player> userMap = new HashMap<>();
    private int totalGames = 0;
    private static Map<String, CheckersGame> activeGames = new HashMap<>();

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
    public static CheckersGame getGame(String whitePlayer){
        return activeGames.getOrDefault(whitePlayer, null);
    }

    public int getTotalGames(){
        return totalGames;
    }

    /**
     * Collect site wide statistics when a game is finished.
     */
    public void gameFinished(){
        synchronized(this){
            totalGames++;
        }
    }

    /**
     * saveUser takes in a user, checks if they are in the userMap, adds them if they are not, or changes their status to
     * SEARCHING if no other user is using that username
     *
     * @param newPlayer - player to add to map/set to SEARCHING
     * @return - addUserStatus enum based on whether or not the user was added
     */
    public PostLoginRoute.AddUserStatus saveUser(Player newPlayer) {
        if( newPlayer.getName() == null || !newPlayer.getName().matches("^[a-zA-Z0-9]*$") || newPlayer.getName().matches(".*\\s+.*")){
            LOG.fine("Not alphaNumeric/spaces");
            System.out.println("invalid char");
            return PostLoginRoute.AddUserStatus.INVLAID;
        }
        if (newPlayer.getName().length() < 1) {
            LOG.fine("Not at least one character");
            System.out.println("too small");
            return PostLoginRoute.AddUserStatus.INVLAID;
        }
        if (userMap.containsKey(newPlayer.getName())) {
            if(userMap.get(newPlayer.getName()).status == Player.Status.OFFLINE){
                newPlayer.status = Player.Status.SEARCHING;
                increment();
                return PostLoginRoute.AddUserStatus.SUCCESS;
            }
            else {
                return PostLoginRoute.AddUserStatus.PICKANOTHER;
            }
        }
        else{
            userMap.put(newPlayer.getName(), newPlayer);
            increment();
            return PostLoginRoute.AddUserStatus.SUCCESS;
        }
    }

    /**
     * getLiveCount get's the number of players that are logged in
     * @return number of players that are logged in
     */
    public static int getLiveCount() {
        return liveCount;
    }

    /**
     * increment increments the liveCount (number of players online)
     */
    public static void increment() {
        liveCount++;
    }

    /**
     * decrement decrements the liveCount (number of players online)
     */
    public static void decrement() {
        liveCount--;
    }

    /**
     * getWaitingPlayer get's the players who's status is set to WAITING and adds them to waitingPlayers
     *
     * @return waitingPlayers array List
     */
    public ArrayList<Player> getWaitingPlayer() {
        userMap.forEach((s, player) -> {
            if(player.status == Player.Status.WAITING) {
                if (!waitingPlayers.contains(player)) {
                    waitingPlayers.add(player);
                }
            }
        });
        return waitingPlayers;
    }

    /**
     * removeUser removes a user from the list of online players (onlinePlayers)
     *
     * @param player player instance to be removed from onlinePlayers
     * @return boolean based on whether or not the remove was successful
     */
    public boolean removeUser(Player player){
        // checking if user exists in array just in case
        for(int i = 0; i < onlinePlayers.size(); i ++) {
            if (onlinePlayers.get(i).equals(player)) {
                onlinePlayers.remove(player);
                return true;
            }
        }
        return false;
    }

    /**
     * newGame creates a new CheckersGame instance and adds it to the activeGames array
     *
     * @param whitePlayer Player that is starting the game
     */
    public static void newGame(Player whitePlayer){
        activeGames.put(whitePlayer.name, new CheckersGame(whitePlayer, null));
    }

    /**
     * addOnlinePlayer adds a player to the onlinePlayers list
     *
     * @param player player instance to be added
     */
    public void addOnlinePlayer(Player player){
        onlinePlayers.add(player);
    }

    /**
     * findPlayer looks to see if a player with the given username is in the userMap, default is null if it is not
     *
     * @param username username of player to check for
     * @return player from map or null if not present
     */
    public Player findPlayer(String username){
        return userMap.getOrDefault(username, null);
    }
}
