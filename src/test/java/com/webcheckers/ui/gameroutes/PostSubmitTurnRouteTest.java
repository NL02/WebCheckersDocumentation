package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for PostSubmitTurnRoute
 *
 */
public class PostSubmitTurnRouteTest {
    /**
     * The component-under-test (CuT);
     */
    private PostSubmitTurnRoute CuT;

    // mock objects
    private Request request;
    private Session session;
    private Response response;
    private Gson gson;
    private Player redPlayer;
    private Player whitePlayer;
    private Game game;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        gson = new Gson();
        when(request.session()).thenReturn(session);

        redPlayer = new Player("redPlayer");
        whitePlayer = new Player("whitePlayer");
        game = new Game(whitePlayer, redPlayer);
        redPlayer.startGame(game);
        whitePlayer.startGame(game);

        CuT = new PostSubmitTurnRoute();

    }

    @Test
    public void test_handleMethod() throws Exception {
        when(session.attribute("currentUser")).thenReturn(redPlayer);
        CuT.handle(request,response);

    }

}
