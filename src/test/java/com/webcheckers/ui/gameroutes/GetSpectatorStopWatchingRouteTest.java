package com.webcheckers.ui.gameroutes;

import com.webcheckers.appl.Player;
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
 * Unit Test Suite for the GetSpectatorStopWatchingRoute handler
 *
 * @author Ethan Davison
 * @author Rayna Mishra
 * @date Last Modified 4/12/20
 */
@Tag("UI-Tier")
public class GetSpectatorStopWatchingRouteTest {

    /** External Objects */
    private Request request;
    private Response response;
    private Session session;

    /** Project-level Objects */
    private Player me;

    /** Component under Test */
    private GetSpectatorStopWatchingRoute CuT;

    /**
     * Setup for all necessary parameters
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);

        me = new Player("Player name");
        when(request.session().attribute("currentUser")).thenReturn(me);
        CuT = new GetSpectatorStopWatchingRoute();
    }

    /**
     * Test to ensure that all attributes are changed correctly and that the user is redirected
     * @throws Exception
     */
    @Test
    public void testHandle() throws Exception{
        // Invoke test
        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        }catch (Exception e) {
            //expected
        }

        //Analyze results
        assertEquals(Player.Status.SEARCHING, me.status);
        verify(response).redirect(WebServer.HOME_URL);
    }
}
