package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for PostValidateTurnRoute
 *
 * @author Nelson Liang
 */
@Tag("UI-tier")
public class PostValidateTurnRouteTest {
    /**
     * The component-under-test (CuT);
     */
    private PostValidateMoveRoute CuT;

    //mock objects
    private Request request;
    private Session session;
    private Response response;
    private Player player;
    private Gson gson;
    private Game game;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        CuT = new PostValidateMoveRoute();
        gson = new Gson();

        player = mock(Player.class);
        game = mock(Game.class);

        when(request.session()).thenReturn(session);



    }

    @Test
    public void test_handleMethod() throws Exception {
        Move move = new Move(new Position(2, 3), new Position(3, 4));
        when(session.attribute(PostValidateMoveRoute.CURRENT_USER_ATTR)).thenReturn(player);
        when(request.body()).thenReturn(gson.toJson(move));
        when(player.getGame()).thenReturn(game);

        CuT.handle(request,response);
    }
}
