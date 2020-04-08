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
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.templateEngine = mock(TemplateEngine.class);
        this.engineTester = new TemplateEngineTester();

        this.playerLobby = mock(PlayerLobby.class);
        this.me = mock(Player.class);
        this.game = mock(Game.class);

        this.CuT = new GetSpectateGameRoute(playerLobby, templateEngine);
    }

    /**
     * Test for the handle() method to ensure correct rendering
     *  when the game is not over
     */
    @Test
    public void testNotOver() throws Exception{
        request.session().attribute("currentUser", me);
        CuT.handle(request, response);

        assert(me.status == Player.Status.SPECTATING);
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute("title", "Spectating a Game");
        engineTester.assertViewModelAttribute("viewMode", "SPECTATOR");
        engineTester.assertViewModelAttributeIsAbsent("modeOptionsAsJSON");
        engineTester.assertViewName("/game");
    }

    /**
     * Test for the handle() method to ensure correct rendering
     *  when the game is over
     */
    @Test
    public void testIsOver() throws Exception{
        request.session().attribute("currentUser", me);
        game.gameOver("GAME IS OVER", mock(Player.class));
        CuT.handle(request, response);

        Map<String, Object> options = new HashMap<>();
        options.put("isGameOver", true);
        options.put("gameOverMessage", "GAME IS OVER");
        assert(me.status == Player.Status.SPECTATING);
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute("title", "Spectating a Game");
        engineTester.assertViewModelAttribute("viewMode", "SPECTATOR");
        engineTester.assertViewModelAttribute("modeOptionsAsJSON", options);
        engineTester.assertViewName("/game");
    }


}
