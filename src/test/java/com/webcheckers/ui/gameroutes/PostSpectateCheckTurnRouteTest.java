package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;

/**
 * Test Suite for the PostSpectatorCheckTurnRouteTest handler
 *
 * @author Ethan Davison, May Wu, Rayna Mishra
 * @date Last Modified: 4/13/20
 */
@Tag("UI-Tier")
public class PostSpectateCheckTurnRouteTest {

    /**
     * The component-under-test (CuT);
     */
    private PostSpectateCheckTurnRoute CuT;

    //mock objects
    private Request request;
    private Session session;
    private Response response;
    private Gson gson;
    private Game game;
    private Player player;

    /**
     * Setup for all necessary mock objects
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        gson = new Gson();
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        game = mock(Game.class);

        CuT = new PostSpectateCheckTurnRoute();
    }

    /**
     * test_handlMethod_Red tests the handle method when the active color doesn't
     * match the lastActiveColor variable
     * @throws Exception
     */
    @Test
    public void test_handleMethod_Red() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getActiveColor()).thenReturn(Color.RED);
        Message expected = Message.info("false");
        assertEquals(gson.toJson(expected), CuT.handle(request, response));

    }

    /**
     * test_handlMethod_Red tests the handle method when the active color does
     * match the lastActiveColor variable
     * @throws Exception
     */
    @Test
    public void test_handleMethod_White() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getActiveColor()).thenReturn(Color.WHITE);
        Message expected = Message.info("true");
        assertEquals(gson.toJson(expected), CuT.handle(request, response));
    }

}
