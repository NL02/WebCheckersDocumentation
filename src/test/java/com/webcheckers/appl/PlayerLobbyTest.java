package com.webcheckers.appl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.pageroutes.PostLoginRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.appl.PlayerLobby;

import java.util.ArrayList;

/**
 * The unit test suite for the PlayerLobby component
 *
 * @author Rayna Mishra rvm8343
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    /**
     * The component-under-test (CuT)
     */
    private PlayerLobby CuT;

    // mock objects
    private Player player1;
    private Player player2;
    private Player player3;
    private CheckersGame game;

    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();
    }

    /**
     * Test the ability to make a new PlayerService
     */
    @Test
    public void test_make_player_service(){
        // Invoke test
        final PlayerServices playerServices = CuT.newPlayerServices();
        // check result
        assertNotNull(playerServices);
    }

    /**
     * Test the ability to make a new CheckersGame game
     */
    @Test
    public void test_make_game(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Avdol");
        CuT.saveUser(player1);

        when(new CheckersGame(player1)).thenReturn(game);

        // Invoke test
        CuT.newGame(player1);
        CheckersGame gameMade = CuT.getGame("Avdol");

        // Check results
        // 1) The returned game is not null
        assertEquals(game, gameMade);
    }

    /**
     * Test the ability to increment the total number of games
     */
    @Test
    public void test_game_finished(){
        int totalGames = CuT.getTotalGames();
        // Invoke test
        CuT.gameFinished();
        int totalGamesAfter = CuT.getTotalGames();

        // Analyze results:
        assertEquals(totalGames + 1, totalGamesAfter);
    }

    /**
     * Test the ability to not save a user with an invalid user name -- no name given (null)
     */
    @Test
    public void test_save_user_null_name(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn(null);

        // Invoke test
        PostLoginRoute.AddUserStatus result = CuT.saveUser(player1);

        // Analyze results:
        assertEquals(PostLoginRoute.AddUserStatus.INVLAID, result);

    }

    /**
     * Test the ability to not save a user with an invalid user name -- name with invalid char
     */
    @Test
    public void test_save_user_invalid_char_name(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("weruio{}weruio");

        // Invoke test
        PostLoginRoute.AddUserStatus result = CuT.saveUser(player1);

        // Analyze results:
        assertEquals(PostLoginRoute.AddUserStatus.INVLAID, result);
    }

    /**
     * Test the ability to not save a user that has an empty string for a username
     */
    @Test
    public void test_save_user_empty_string(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("");

        //Invoke test
        PostLoginRoute.AddUserStatus result = CuT.saveUser(player1);

        //Analyze results:
        assertEquals(PostLoginRoute.AddUserStatus.INVLAID, result);
    }

    /**
     * Test the ability to save a user that has just logged in for the first time
     */
    @Test
    public void test_save_user_new_name(){
        player1 = mock(Player.class);
        player1.status = Player.Status.SEARCHING;
        when(player1.getName()).thenReturn("Johnny");

        // Invoke test
        PostLoginRoute.AddUserStatus first = CuT.saveUser(player1);

        // Analyze results:
        assertEquals(PostLoginRoute.AddUserStatus.SUCCESS, first);
        assertEquals(CuT.findPlayer("Johnny"), player1);
        assertEquals(Player.Status.SEARCHING, player1.status);
    }

    /**
     * Test the ability to not save a user that gave a name that is already in use
     */
    @Test
    public void test_save_user_need_new_name(){
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Johnathan");
        when(player1.getName()).thenReturn("Johnathan");

        //Invoke test
        PostLoginRoute.AddUserStatus first = CuT.saveUser(player1);
        PostLoginRoute.AddUserStatus second = CuT.saveUser(player2);

        //Analyze results:
        assertEquals(PostLoginRoute.AddUserStatus.SUCCESS, first);
        assertEquals(PostLoginRoute.AddUserStatus.PICKANOTHER, second);
        assertEquals(CuT.findPlayer("Johnathan"), player1);
    }


    /**
     * Test the ability to save a user that has just logged in and is a returning player
     */
    @Test
    public void test_save_user_returning_player(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Joseph");
        PostLoginRoute.AddUserStatus first = CuT.saveUser(player1);
        player1.status = Player.Status.OFFLINE;

        // Invoke test
        PostLoginRoute.AddUserStatus second = CuT.saveUser(player1);

        // Analyze results:
        assertEquals(PostLoginRoute.AddUserStatus.SUCCESS, first);
        assertEquals(PostLoginRoute.AddUserStatus.SUCCESS, second);
        assertEquals(CuT.findPlayer("Joseph"), player1);
        assertEquals(Player.Status.SEARCHING, player1.status);
    }

    /**
     * Test the ability to get a list of waiting players when there are no waiting players
     */
    @Test
    public void test_get_waiting_players_none(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Jotaro");
        CuT.saveUser(player1);
        player1.status = Player.Status.OFFLINE;

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Josuke");
        CuT.saveUser(player2);
        player2.status = Player.Status.SEARCHING;

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn("Giorno");
        CuT.saveUser(player3);
        player3.status = Player.Status.INGAME;

        // Invoke test
        ArrayList<Player> playerList = CuT.getWaitingPlayer();

        // Analyze results:
        assertEquals(0, playerList.size());
    }

    /**
     * Test the ability to get a list of waiting players when there is one waiting player
     */
    @Test
    public void test_get_waiting_players_one(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Jotaro");
        CuT.saveUser(player1);
        player1.status = Player.Status.WAITING;

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Josuke");
        CuT.saveUser(player2);
        player2.status = Player.Status.SEARCHING;

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn("Giorno");
        CuT.saveUser(player3);
        player3.status = Player.Status.INGAME;

        // Invoke test
        ArrayList<Player> playerList = CuT.getWaitingPlayer();

        // Analyze results:
        assertEquals(1, playerList.size());
        assertEquals(player1, playerList.get(0));
    }

    /**
     * Test the ability to get a list of waiting players when there are two waiting players
     */
    @Test
    public void test_get_waiting_players_two(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Jotaro");
        CuT.saveUser(player1);
        player1.status = Player.Status.WAITING;

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Josuke");
        CuT.saveUser(player2);
        player2.status = Player.Status.WAITING;

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn("Giorno");
        CuT.saveUser(player3);
        player3.status = Player.Status.INGAME;

        // Invoke test
        ArrayList<Player> playerList = CuT.getWaitingPlayer();

        // Analyze results:
        assertEquals(2, playerList.size());
        assertEquals(player1, playerList.get(0));
        assertEquals(player2, playerList.get(1));
    }

    /**
     * Test the ability to remove a user from the list of online players
     */
    @Test
    public void test_remove_user_online(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Jolyne");
        CuT.saveUser(player1);
        player1.status = Player.Status.SEARCHING;
        CuT.addOnlinePlayer(player1);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Gappy");
        CuT.saveUser(player2);
        player2.status = Player.Status.SEARCHING;
        CuT.addOnlinePlayer(player2);

        // Invoke test
        boolean result = CuT.removeUser(player1);
        ArrayList<Player> onlinePlayersList = CuT.getOnlinePlayers();

        // Analyze results:
        assertEquals(true, result);
        assertEquals(1, onlinePlayersList.size());
        assertEquals(player2, onlinePlayersList.get(0));
    }

    /**
     * Test the ability to add a player to the list of online players
     */
    @Test
    public void test_add_online_player(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Jolyne");
        CuT.saveUser(player1);
        player1.status = Player.Status.OFFLINE;

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Gappy");
        CuT.saveUser(player2);
        player2.status = Player.Status.SEARCHING;

        // Invoke test
        boolean result1 = CuT.addOnlinePlayer(player1);
        boolean result2 = CuT.addOnlinePlayer(player2);
        ArrayList<Player> onlinePlayersList = CuT.getOnlinePlayers();

        // Analyze results:
        assertEquals(false, result1);
        assertEquals(true, result2);
        assertEquals(1, onlinePlayersList.size());
        assertEquals(player2, onlinePlayersList.get(0));
    }

    /**
     * Test the ability to find a player in the user Map when the given player is null
     */
    @Test
    public void test_find_player_null(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Bruno");
        CuT.saveUser(player1);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Mista");
        CuT.saveUser(player2);

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn("Fugo");
        CuT.saveUser(player3);

        // Invoke test
        Player result1 = CuT.findPlayer(null);
        Player result2 = CuT.findPlayer("Mista");

        // Analyze results:
        assertNull(result1);
        assertEquals(player2, result2);
    }

    /**
     * Test the ability to find a player in the user Map when the player object is not in the map
     */
    @Test
    public void test_find_player_non_existent(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Bruno");
        CuT.saveUser(player1);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Mista");
        CuT.saveUser(player2);

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn("Fugo");
        CuT.saveUser(player3);

        // Invoke test
        Player result1 = CuT.findPlayer("Narancia");
        Player result2 = CuT.findPlayer("Bruno");


        // Analyze results:
        assertNull(result1);
        assertEquals(player1, result2);
    }

    /**
     * Test the ability to find a player in the user Map when the given player is valid
     */
    @Test
    public void test_find_player_valid(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Bruno");
        CuT.saveUser(player1);

        player2 = mock(Player.class);
        when(player2.getName()).thenReturn("Mista");
        CuT.saveUser(player2);

        player3 = mock(Player.class);
        when(player3.getName()).thenReturn("Fugo");
        CuT.saveUser(player3);

        // Invoke test
        Player result1 = CuT.findPlayer("Bruno");
        Player result2 = CuT.findPlayer("Mista");
        Player result3 = CuT.findPlayer("Fugo");

        // Analyze results:
        assertEquals(player1, result1);
        assertEquals(player2, result2);
        assertEquals(player3, result3);
    }


}
