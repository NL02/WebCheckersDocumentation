package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Tag("UI-Tier")
public class GetHomeRouteTest {
    /**
     * Component under test, or CuT
     */
    private GetHomeRoute CuT;

    // friendly objects
    private PlayerLobby playerLobby;

    // mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    private Player currUser;
    private ArrayList<Player> onlinePlayers;
    private Collection<Game> gameList;
    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        onlinePlayers = mock(ArrayList.class);
        gameList = mock(ArrayList.class);
        playerLobby = mock(PlayerLobby.class);

        // Create a unique CuT for each test
        CuT = new GetHomeRoute(playerLobby,engine);
    }

    @Test
    public void test_validPage() throws Exception {
        currUser = new Player("validPlayer");
        when(session.attribute("currentUser")).thenReturn(currUser);
        when(playerLobby.getWaitingPlayer()).thenReturn(onlinePlayers);
        when(playerLobby.getAllGames()).thenReturn(gameList);

//        final TemplateEngineTester testHelper = new TemplateEngineTester();

//        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request,response);
//        testHelper.assertViewModelExists();
//        testHelper.assertViewModelIsaMap();
//
////        Message num_players = Message.info(String.format(GetHomeRoute.NUM_PLAYERS_MSG, playerLobby.getLiveCount()));
//
//
//        testHelper.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR,GetHomeRoute.WELCOME_MSG);
////        testHelper.assertViewModelAttribute(GetHomeRoute.NUM_PLAYERS_ATTR,num_players);
//        testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR,session.attribute("currentUser"));
//        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_LIST_ATTR,playerLobby.getWaitingPlayer());
//        testHelper.assertViewModelAttribute(GetHomeRoute.GAME_LIST_ATTR,playerLobby.getAllGames());
//        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    @Test
    public void test_redirect() throws Exception {
        currUser = new Player("validPlayer");
        currUser.status = Player.Status.INGAME;
        assertNotNull(currUser);
        when(session.attribute("currentUser")).thenReturn(currUser);
        when(playerLobby.getWaitingPlayer()).thenReturn(onlinePlayers);
        when(playerLobby.getAllGames()).thenReturn(gameList);
        CuT.handle(request, response);

        //Analyze the results:
        //* redirects to the GameView
        assertEquals(Player.Status.INGAME, currUser.status);

        verify(response).redirect(WebServer.GAME_URL);
    }
    @Test
    public void test_Searching() throws Exception {
        currUser = new Player("validPlayer");
        assertNotNull(currUser);
        currUser.status = Player.Status.WAITING;
        when(session.attribute("currentUser")).thenReturn(currUser);
        when(playerLobby.getWaitingPlayer()).thenReturn(onlinePlayers);
        when(playerLobby.getAllGames()).thenReturn(gameList);
        CuT.handle(request, response);
        //Analyze the results:
        //* redirects to the GameView
        assertEquals(Player.Status.SEARCHING, currUser.status);
    }
}
