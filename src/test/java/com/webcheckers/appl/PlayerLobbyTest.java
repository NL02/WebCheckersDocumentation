package com.webcheckers.appl;
import com.webcheckers.model.Game;
import com.webcheckers.ui.pageroutes.PostLoginRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();
    }


    /**
     * Test the ability to make a new CheckersGame game
     */
    @Test
    public void test_make_game(){
        player1 = mock(Player.class);
        when(player1.getName()).thenReturn("Avdol");
        CuT.saveUser(player1);


        // Invoke test
        CuT.newGame(player1);
        Game gameMade = CuT.getGame(player1.name);

        // Check results
        // 1) The returned game is not null
        assertNotNull(gameMade);
    }

//    /**
//     * Test the ability to get all active games in a list
//     */
//    @Test
//    public void test_get_all_games(){
//        player1 = mock(Player.class);
//        when(player1.getName()).thenReturn("Gyro");
//        player2 = mock(Player.class);
//        when(player2.getName()).thenReturn("Caesar");
//        player3 = mock(Player.class);
//        when(player3.getName()).thenReturn("Will");
//        Player ghost = mock(Player.class);
//        when(ghost.getName()).thenReturn("Waiting for Player");
//
//        Game game1 = mock(Game.class);
//        when(game1.getRedPlayer()).thenReturn(player2);
//        Game game2 = mock(Game.class);
//        when(game2.getRedPlayer()).thenReturn(ghost);
//
//        CuT.addGame(player1, game1);
//        CuT.addGame(player2, game1);
//        CuT.addGame(player3, game2);
//        CuT.addGame(ghost, game2);
//
//        /// Invoke Test
//        Collection<Game> gameList = CuT.getAllGames();
//        assertEquals(1, gameList.size());
//        assertTrue(gameList.contains(game1));
//    }

    /**
     * Test ability to increment liveCount
     */
    @Test
    public void test_increment(){
        int liveCount = CuT.getLiveCount();

        // Invoke test
        PlayerLobby.increment();
        int liveCountAfter = CuT.getLiveCount();

        assertEquals(liveCount + 1, liveCountAfter);
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
