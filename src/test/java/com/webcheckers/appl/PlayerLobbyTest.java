package com.webcheckers.appl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.ui.pageroutes.PostLoginRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.webcheckers.appl.PlayerLobby;

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

        // Invoke test
        final CheckersGame game = CuT.getGame("Player1");

        // Check results
        // 1) The returned game is not null
        assertNotNull(game);
        // 2) The whitePlayer is "Player1"
        assertEquals("Player1", game.getWhitePlayer().getName());
        // 3) The opponent is null
        assertNull(game.getRedPlayer().getName());
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
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to get a list of waiting players when there is one waiting player
     */
    @Test
    public void test_get_waiting_players_one(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to get a list of waiting players when there are two waiting players
     */
    @Test
    public void test_get_waiting_players_two(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to remove an online user from the list of online players
     */
    @Test
    public void test_remove_user_online(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to remove an offline user from the list of online players
     */
    @Test
    public void test_remove_user_offline(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to add a player to the list of online players
     */
    @Test
    public void test_add_online_player(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to find a player in the user Map when the given player is null
     */
    @Test
    public void test_find_player_null(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to find a player in the user Map when the player object is not in the map
     */
    @Test
    public void test_find_player_non_existent(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to find a player in the user Map when the given player is valid
     */
    @Test
    public void test_find_player_valid(){
        // Invoke test
        // Analyze results:
    }


}
