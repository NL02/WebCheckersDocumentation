package com.webcheckers.appl;

import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.ui.pageroutes.PostLoginRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
    private static int totalGames = 0;
    public static Map<String, Game> activeGames = new HashMap<>();
    //private static Map<String, Game> allGames = TODO Find Valid Data Type to store all games

    //
    // Public methods
    //


    /**
     * Create a new {CheckersGame} game.
     *
     * @return
     *   A new {@link Game}
     */
    public static Game getGame(String whitePlayer){
        return activeGames.getOrDefault(whitePlayer, null);
        //TODO Switch to allGames once implemented
    }

    /**
     * Collect site wide statistics when a game is finished.
     */
    private static void gameFinished(){
            totalGames++;
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
            return PostLoginRoute.AddUserStatus.INVLAID;
        }
        if (newPlayer.getName().length() < 1) {
            LOG.fine("Not at least one character");
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
            else{
                if(waitingPlayers.contains(player)){
                    waitingPlayers.remove(player);
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

    public static int getTotalGames(){
        return totalGames;
    }

    /**
     * newGame creates a new CheckersGame instance and adds it to the activeGames array
     *
     * @param whitePlayer Player that is starting the game
     */
    public static void newGame(Player whitePlayer){
        activeGames.put(whitePlayer.name, new Game(whitePlayer, null));
    }

    public static void addGame(Player player, Game game){
        activeGames.put(player.name, game);
        //TODO Add game to allGames
    }

    /**
     * removeGame removes a game from the activeGames list
     *
     * @param player that is starting the game
     */
    public static void removeGame(Player player){
        gameFinished();
        activeGames.remove(player.name);
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
