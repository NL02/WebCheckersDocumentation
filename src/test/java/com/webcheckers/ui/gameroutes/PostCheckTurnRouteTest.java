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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit testing for PostCheckTurnRoute
 *
 * @author May Wu
 */
@Tag("UI-Tier")
public class PostCheckTurnRouteTest {

    /**
     * Component under test, or CuT
     */
    private PostCheckTurnRoute CuT;

    //mock objects
    private Request request;
    private Session session;
    private Response response;
    private Gson gson;
    private Game game;

    private Player redPlayer;
    private Player whitePlayer;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        gson = new Gson();
        when(request.session()).thenReturn(session);

        redPlayer = new Player("Red");
        whitePlayer = new Player("White");
        game = new Game(whitePlayer, redPlayer);
        redPlayer.startGame(game);
        whitePlayer.startGame(game);

        // Create a unique CuT for each test
        CuT = new PostCheckTurnRoute();
    }

    /**
     * Test that a user correctly checks turn if it is true
     * @throws Exception
     */
    @Test
    public void test_true() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);

        Message expected = Message.info("true");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));

    }

    /**
     * Test that a user correctly checks turn if it is false
     * @throws Exception
     */
    @Test
    public void test_false() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(whitePlayer);

        Message expected = Message.info("false");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));

    }
}
