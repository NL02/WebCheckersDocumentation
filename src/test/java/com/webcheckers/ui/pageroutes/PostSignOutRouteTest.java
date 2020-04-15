package com.webcheckers.ui.pageroutes;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the PostSignOutTester
 *
 * @author Rayna Mishra rvm8343
 */
@Tag("UI-tier")
public class PostSignOutRouteTest {

    /**
     * The component-under-test (CuT)
     */
    private PostSignOutRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private Response response;
    private Game game;

    // friendly
    public PlayerLobby lobby;
    public Player player;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);

        lobby = new PlayerLobby();
        player = new Player("Player name");
        CuT = new PostSignOutRoute(lobby);
    }

    /**
     * Test ability to remove a player from the online players list given a player that was
     * searching for a game.
     */
    @Test
    public void removePlayerWasSearching() throws Exception {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);

        // Invoke test
        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        }catch (Exception e) {
            //expected
        }
        // Analyze results:
        assertEquals(Player.Status.OFFLINE, player.status);
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test ability to remove a player from the online players list given a player that was
     * waiting for a game.
     */
    @Test
    public void removePlayerWasWaiting() throws Exception {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
        player.status = Player.Status.WAITING;

        // Invoke test
        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        }catch (Exception e) {
            //expected
        }
        // Analyze results:
        assertEquals(Player.Status.OFFLINE, player.status);
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test ability to remove a player from the online players list given a player that was
     * in a game.
     */
    @Test
    public void removePlayerWasInGame() throws Exception {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
        final String GAME_OVER_MSG = "%s has resigned from the game";
        game = mock(Game.class);
        player.startGame(game);


        player.status = Player.Status.INGAME;

        // Invoke test

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        }catch (Exception e) {
            //expected
        }
        // Analyze results:
        assertEquals(Player.Status.OFFLINE, player.status);
        assertNotEquals(player.game, game);
        verify(response).redirect(WebServer.HOME_URL);
    }
}
