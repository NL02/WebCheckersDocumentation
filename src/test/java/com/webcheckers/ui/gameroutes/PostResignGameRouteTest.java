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
 * Unit tests for PostResignGameRoute
 *
 * @author May Wu
 */
@Tag("UI-Tier")
public class PostResignGameRouteTest {

    /**
     * Component under test
     */
    private PostResignGameRoute CuT;

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

        redPlayer = new Player("Red");
        whitePlayer = new Player("White");
        game = new Game(whitePlayer, redPlayer);
        redPlayer.startGame(game);
        whitePlayer.startGame(game);

        CuT = new PostResignGameRoute();
    }

    /**
     * Test that player cannot resign if they are not in game
     * @throws Exception
     */
    @Test
    public void test_notInGame() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        redPlayer.status = Player.Status.SEARCHING;

        Message expected = Message.error("You can't resign if you haven't started a game!");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));
    }

    /**
     * Test that player can resign successfully
     * @throws Exception
     */
    @Test
    public void test_goodResign() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        redPlayer.status = Player.Status.INGAME;

        Message expected = Message.info("true");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));
    }
}
