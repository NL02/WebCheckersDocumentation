package com.webcheckers.ui.pageroutes.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.ui.gameroutes.PostSpectateCheckTurnRoute;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

/**
 * Test Suite for the PostSpectatorCheckTurnRouteTest handler
 *
 * @author Ethan Davison
 * @date Last Modified: 8/4/20
 */
public class PostSpectatorCheckTurnRouteTest {

    /** External Objects */
    private Request request;
    private Response response;
    private Session session;

    /** Project Level Objects */
    private Player me;
    private Game game;

    /** Class under Testing */
    private PostSpectateCheckTurnRoute CuT;

    /**
     * Setup for all necessary mock objects
     */
    @BeforeEach
    public void setup(){
    }

    /**
     * Test for the handle() method to ensure the correct response is given
     */
    @Test
    public void testHandle() throws Exception {
    }
}