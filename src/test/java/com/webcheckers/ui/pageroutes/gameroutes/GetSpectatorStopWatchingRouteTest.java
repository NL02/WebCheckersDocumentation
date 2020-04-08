package com.webcheckers.ui.pageroutes.gameroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.gameroutes.GetSpectatorStopWatchingRoute;
import com.webcheckers.ui.pageroutes.PostSignOutRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit Test Suite for the GetSpectatorStopWatchingRoute handler
 *
 * @author Ethan Davison
 * @date Last Modified 8/4/20
 */
@Tag("UI-Tier")
public class GetSpectatorStopWatchingRouteTest {

    /** External Objects */
    private Request request;
    private Response response;
    private Session session;

    /** Project-level Objects */
    private Player me;

    /** Class under Test */
    private GetSpectatorStopWatchingRoute CuT;

    /**
     * Setup for all necessary parameters
     */
    @BeforeEach
    public void setup(){
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);

        this.me = mock(Player.class);

        this.CuT = new GetSpectatorStopWatchingRoute(mock(PlayerLobby.class), mock(TemplateEngine.class));
    }

    /**
     * Test to ensure that all attributes are changed correctly and that the user is redirected
     * @throws Exception
     */
    @Test
    public void testHandle() throws Exception{
        when(session.attribute("currentUser")).thenReturn(me);
        CuT.handle(request, response);

        assert(me.status == Player.Status.SEARCHING);
        assert(me.game == null);
        assert(request.url().equals("/"));

    }
}
