package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.*;

/**
 * Test class for PostLoginRoute Test
 *
 * @author Nelson Liang
 */

@Tag("UI-Tier")
public class PostLoginRouteTest {
    /**
     * Component under test, or CuT
     */
    private PostLoginRoute CuT;

    // friendly objects
    private PlayerLobby playerLobby;

    //mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    private Player currUser;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        // Create a unique CuT for each test
        CuT = new PostLoginRoute(playerLobby,engine);
    }

    /**
     * Test that a player gets made
     * @throws Exception
     */
    @Test
    public void test_creatingPlayer() throws Exception {
        currUser = new Player("validPlayer");
        when(playerLobby.findPlayer("anything")).thenReturn(null);
        when(playerLobby.saveUser(currUser)).thenReturn(PostLoginRoute.AddUserStatus.SUCCESS);

        CuT.handle(request, response);
        verifyZeroInteractions(response);

    }

    /**
     * Test that a message gets thrown when a user enters an already used name
     */
    @Test
    public void test_sameName() throws Exception {
        currUser = new Player("validPlayer");
        when(request.queryParams("username")).thenReturn("validPlayer");
        when(playerLobby.findPlayer("validPlayer")).thenReturn(currUser);

        CuT.handle(request,response);
        verifyZeroInteractions(response);
    }

    /**
     * This test makse sure that a user is assigned the correct player object
     */
    @Test
    public void test_existingUser() throws Exception {
        currUser = new Player("validPlayer");
        when(request.queryParams("username")).thenReturn("validPlayer");
        when(playerLobby.findPlayer("validPlayer")).thenReturn(currUser);
        currUser.status = Player.Status.OFFLINE;

        CuT.handle(request,response);
        verify(response).redirect(WebServer.HOME_URL);

    }

    @Test
    public void test_addUserInvalid() throws Exception {
        currUser = new Player("");
        when(request.queryParams("username")).thenReturn(".");
        when(playerLobby.saveUser(currUser)).thenReturn(PostLoginRoute.AddUserStatus.INVLAID);

        CuT.handle(request,response);
        verifyZeroInteractions(response);

    }


}
