package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * Unit Test Suite for the GetSpectatorGameRoute handler
 *
 * @author Ethan Davison
 * @date Last modified: 8/4/20
 */
@Tag("UI-Tier")
public class GetSpectateGameRouteTest {

    /** External Objects */
    private Request request;
    private Response response;
    private TemplateEngine templateEngine;
    private TemplateEngineTester engineTester;

    /** Project-level Objects */
    private PlayerLobby playerLobby;
    private Game game;
    private Player me;

    /** Class under Testing */
    private GetSpectateGameRoute CuT;


    /**
     * Set up new mock objects to test on.
     */
    @BeforeEach
    public void setup(){
    }

    /**
     * Test for the handle() method to ensure correct rendering
     *  when the game is not over
     */
    @Test
    public void testNotOver() throws Exception{
    }

    /**
     * Test for the handle() method to ensure correct rendering
     *  when the game is over
     */
    @Test
    public void testIsOver() throws Exception{
    }


}
