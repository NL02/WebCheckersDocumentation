package com.webcheckers.appl;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
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
public class PlayerLobbyTester {

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
        final PlayerLobby CuT = new PlayerLobby();
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
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to not save a user with an invalid user name -- no name given (null)
     */
    @Test
    public void test_save_user_null_name(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to not save a user with an invalid user name -- name with invalid char
     */
    @Test
    public void test_save_user_invalid_char_name(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to not save a user that gave a name that is already in use
     */
    @Test
    public void test_save_user_need_new_name(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to save a user that has just logged in for the first time
     */
    @Test
    public void test_save_user_new_name(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to save a user that has just logged in and is a returning player
     */
    @Test
    public void test_save_user_returning_player(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to get the count of online players (live count)
     */
    @Test
    public void test_get_live_count(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to increment the number of online players (live count)
     */
    @Test
    public void test_increment(){
        // Invoke test
        // Analyze results:
    }

    /**
     * Test the ability to decrement the count of online players (live count)
     */
    @Test
    public void test_decrement(){
        // Invoke test
        // Analyze results:
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
