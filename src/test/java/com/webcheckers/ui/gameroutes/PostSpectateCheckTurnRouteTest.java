package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
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
 * @author Ethan Davison, May Wu
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

    private Player redPlayer;
    private Player whitePlayer;
    private Player spectator;

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

        redPlayer = new Player("Red");
        whitePlayer = new Player("White");
        spectator = new Player("Spectator");
        game = new Game(whitePlayer, redPlayer);
        redPlayer.startGame(game);
        whitePlayer.startGame(game);

        CuT = new PostSpectateCheckTurnRoute();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void test_handleMethod() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(spectator);

        Message expected = Message.info("true");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));

    }

}
