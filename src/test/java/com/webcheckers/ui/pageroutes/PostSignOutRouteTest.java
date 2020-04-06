package com.webcheckers.ui.pageroutes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.io.PipedOutputStream;

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
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private Response response;

    // friendly
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
        lobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);

        player = new Player("Player name");
        CuT = new PostSignOutRoute(lobby, engine);
    }

    /**
     * Test ability to remove a player from the online players list given a player that was
     * searching for a game.
     */
    @Test
    public void removePlayerWasSearching() {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
        when(lobby.removeUser(player)).thenReturn(true);

        // Invoke test
        CuT.handle(request, response);

        // Analyze results:
        assertEquals(true, CuT.getIsRemoved());
        assertEquals(Player.Status.OFFLINE, player.status);
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test ability to remove a player from the online players list given a player that was
     * waiting for a game.
     */
    @Test
    public void removePlayerWasWaiting() {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
        when(lobby.removeUser(player)).thenReturn(true);
        player.status = Player.Status.WAITING;

        // Invoke test
        CuT.handle(request, response);

        // Analyze results:
        assertEquals(true, CuT.getIsRemoved());
        assertEquals(Player.Status.OFFLINE, player.status);
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test ability to remove a player from the online players list given a player that was
     * in a game.
     */
    @Test
    public void removePlayerWasInGame() {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
        when(lobby.removeUser(player)).thenReturn(true);
        player.status = Player.Status.INGAME;

        // Invoke test
        CuT.handle(request, response);

        // Analyze results:
        assertEquals(true, CuT.getIsRemoved());
        assertEquals(Player.Status.OFFLINE, player.status);
        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Test ability to remove a player from the online players list given a player that is
     * not online
     */
    @Test
    public void removePlayerWasNotOnline() {
        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
        when(lobby.removeUser(player)).thenReturn(false);
        player.status = Player.Status.OFFLINE;

        //Invoke Test
        CuT.handle(request, response);

        assertEquals(false, CuT.getIsRemoved());
        assertEquals(Player.Status.OFFLINE, player.status);
        verify(response).redirect(WebServer.HOME_URL);
    }
}
