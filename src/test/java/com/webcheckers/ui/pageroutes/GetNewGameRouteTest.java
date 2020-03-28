package com.webcheckers.ui.pageroutes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit Test for the GetNewGameRoute class
 *
 * @author Ethan Davison
 */
@Tag("UI-Tier")
public class GetNewGameRouteTest {

    /** Test Message */
    private static final Message TEST_MESSAGE = Message.info("Test");

    /** HTML Tags */
    private static final String TITLE_ATTR = "title";
    private static final String TITLE = "Waiting for Opponent";
    private static final String GAME_ID_ATTR = "gameID";
    private static final String CURRENT_USER_ATTR = "currentUser";
    private static final String VIEW_MODE_ATTR = "viewMode";
    private static final String VIEW_MODE = "PLAY";
    private static final String RED_PLAYER_ATTR = "redPlayer";
    private static final String WHITE_PLAYER_ATTR = "whitePlayer";
    private static final String ACTIVE_COLOR_ATTR = "activeColor";
    private static final String ACTIVE_COLOR = "RED";
    private static final String BOARD_ATTR = "board";

    /** Component Under Test */
    private GetNewGameRoute CuT;

    /** Mock Objects */
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private Response response;

    /** Setup of mock objects */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        lobby = mock(PlayerLobby.class);

        CuT = new GetNewGameRoute(engine);
    }

    /**
     * Test of the handle() method to ensure correct rendering
     */
    @Test
    public void testHandle(){}
}
