package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit testing for PostBackupMoveRoute
 *
 * @author May Wu
 */
@Tag("UI-Tier")
public class PostBackUpMoveRouteTest {

    /**
     * Component under test, or CuT
     */
    private PostBackupMoveRoute CuT;

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
        CuT = new PostBackupMoveRoute();
    }

    @Test
    public void test_cannotBackup() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);

        Message expected = Message.error("No moves to back up");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));

    }

    @Test
    public void test_canBackup() throws Exception {
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);

        // makes a move that can be undone
        Move firstMove = new Move(new Position(5,0), new Position(4,1));
        game.validateMove(firstMove);
        Message expected = Message.info("Move undone");

        assertEquals(gson.toJson(expected), CuT.handle(request, response));

    }



}
