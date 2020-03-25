package com.webcheckers.ui.pageroutes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

@Tag("UI-Tier")
public class PostLoginRouteTest {

    // component under test
    private PostLoginRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private Response response;
    private Player player;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        lobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);
        player = mock(Player.class);

        CuT = new PostLoginRoute(lobby, engine);
    }

    @Test
    public void test_good_login() {
        final TemplateEngineTester testEngine = new TemplateEngineTester();
        when(request.queryParams("username")).thenReturn("may");
        when(lobby.saveUser(player)).thenReturn(PostLoginRoute.AddUserStatus.SUCCESS);

        // creates player with given name
        player = new Player(request.queryParams("username"));
        assertNotNull(player, "Player is null");

        // invoke the test
        CuT.handle(request, response);

        // successful login should return null
        assertNull(CuT.handle(request, response));
    }

    @Test
    public void test_bad_login_alpha() {
        final TemplateEngineTester testEngine = new TemplateEngineTester();
        when(session.attribute(PostLoginRoute.MESSAGE_ATTR)).thenReturn(PostLoginRoute.INVALID_USERNAME);
        when(request.queryParams("username")).thenReturn("may;");

        // creates player with given name
        player = new Player(request.queryParams("username"));
        assertNotNull(player, "Player is null");

        //invoke the test
        CuT.handle(request, response);

        // checks that correct message is assigned
        assertEquals(PostLoginRoute.INVALID_USERNAME, session.attribute(PostLoginRoute.MESSAGE_ATTR));
    }

    @Test
    public void test_bad_login_length() {
        final TemplateEngineTester testEngine = new TemplateEngineTester();
        when(session.attribute(PostLoginRoute.MESSAGE_ATTR)).thenReturn(PostLoginRoute.INVALID_USERNAME);
        when(request.queryParams("username")).thenReturn("m");

        // creates player with given name
        player = new Player(request.queryParams("username"));
        assertNotNull(player, "Player is null");

        //invoke the test
        CuT.handle(request, response);

        // checks that correct message is assigned
        assertEquals(PostLoginRoute.INVALID_USERNAME, session.attribute(PostLoginRoute.MESSAGE_ATTR));
    }

}
