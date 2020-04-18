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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private Session session;

    /** Project-level Objects */
    private PlayerLobby playerLobby;
    private Game game;

    /** Class under Testing */
    private GetSpectateGameRoute CuT;


    /**
     * Set up new mock objects to test on.
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        engineTester = mock(TemplateEngineTester.class);
        session = mock(Session.class);

        playerLobby = new PlayerLobby();
        game = mock(Game.class);


        CuT = new GetSpectateGameRoute(templateEngine);
        when(request.queryParams("player")).thenReturn("validPlayer");
        Player me = new Player("validPlayer");
        me.status = Player.Status.SEARCHING;


        game = PlayerLobby.getGame("SomeString");
        when(request.session()).thenReturn(session);
        when(session.attribute(GetSpectateGameRoute.CURRENT_USER_ATTR)).thenReturn(me);

    }

    /**
     * Test correct view model attributes
     */
    @Test
    public void test_viewModel() throws Exception {
        playerLobby = new PlayerLobby();
        Player whitePlayer = new Player("whitePlayer");


        CuT = new GetSpectateGameRoute(templateEngine);
        when(request.queryParams("player")).thenReturn("validPlayer");
        Player me = new Player("validPlayer");
        me.status = Player.Status.SEARCHING;

        game = new Game(whitePlayer,me);
        PlayerLobby.addGame(whitePlayer,game);
        PlayerLobby.addGame(me,game);
        me.startGame(game);
        game = PlayerLobby.getGame(whitePlayer.name);
        when(request.session()).thenReturn(session);
        when(session.attribute(GetSpectateGameRoute.CURRENT_USER_ATTR)).thenReturn(me);
        assertNotNull(game);
        CuT.handle(request, response);
        System.out.println("IT DOESNT HALT");
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(GetSpectateGameRoute.CURRENT_USER_ATTR,me);
        engineTester.assertViewModelAttribute(GetSpectateGameRoute.VIEW_MODE_ATTR,GetSpectateGameRoute.VIEW_MODE);
        engineTester.assertViewModelAttribute(GetSpectateGameRoute.TITLE_ATTR,GetSpectateGameRoute.TITLE_START);
    }

    /**
     * Test for the handle() method to ensure correct rendering
     *  when the game is over
     */
    @Test
    public void testIsOver() throws Exception   {
        playerLobby = new PlayerLobby();
        Player whitePlayer = new Player("whitePlayer");


        CuT = new GetSpectateGameRoute(templateEngine);
        when(request.queryParams("player")).thenReturn("validPlayer");
        Player me = new Player("validPlayer");
        me.status = Player.Status.SEARCHING;

        game = new Game(whitePlayer,me);
        PlayerLobby.addGame(whitePlayer,game);
        PlayerLobby.addGame(me,game);
        me.startGame(game);
        game = PlayerLobby.getGame(whitePlayer.name);
        when(request.session()).thenReturn(session);
        when(session.attribute(GetSpectateGameRoute.CURRENT_USER_ATTR)).thenReturn(me);
        assertNotNull(game);

        game.gameOver("notNull", me);

        CuT.handle(request,response);

    }


}
