package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The unit test suit for GetGameRoute
 *
 * @author Rayna Mishra rvm8343
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    /**
     * The component-under-test (CuT);
     */
    private GetGameRoute CuT;

    //mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;

    // friendly
    private Game game;
    public Player player;
    private PlayerLobby lobby;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        lobby = new PlayerLobby();
        player = new Player("Player Name");
        CuT = new GetGameRoute(engine);

        when(session.attribute(PostSignOutRoute.CURRENT_USER_ATTR)).thenReturn(player);
    }

    /**
     * Tests for making a new game and waiting for an opponent
     * @throws Exception
     */
    @Test
    public void TestNewGame() throws Exception {
        when(request.queryParams("opponent")).thenReturn(null);
        player.status = Player.Status.SEARCHING;
        Player ghost = new Player("Waiting for Player");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_USER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.VIEW_MODE);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE_WAIT);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, ghost);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR_ATTR, Color.RED);

        assertEquals(Player.Status.WAITING, player.status);
    }

    /**
     * Tests for joining a game as a red player
     * @throws Exception
     */
    @Test
    public void JoinGame() throws Exception {
        when(request.queryParams("opponent")).thenReturn("Oppo");
        Player oppo = new Player("Oppo");
        oppo.status = Player.Status.SEARCHING;
        lobby.saveUser(oppo);

        PlayerLobby.newGame(oppo);
        game = PlayerLobby.getGame(oppo.name);
        oppo.startGame(game);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_USER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.VIEW_MODE);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE_YOUR_TURN);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, oppo);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR_ATTR, Color.RED);

        assertEquals(Player.Status.INGAME, player.status);
        assertEquals(Player.Status.INGAME, oppo.status);
    }

    /**
     * Tests for when the game is over
     * @throws Exception
     */
    @Test
    public void EndGameTest() throws Exception {
        when(request.queryParams("opponent")).thenReturn("Oppo");
        Player oppo = new Player("Oppo");
        oppo.status = Player.Status.INGAME;
        player.status = Player.Status.ENDGAME;
        lobby.saveUser(oppo);

        PlayerLobby.newGame(oppo);
        game = PlayerLobby.getGame(oppo.name);
        oppo.startGame(game);
        player.startGame(game);
        PlayerLobby.addGame(player, game);
        game.addRedPlayer(player);
        game.winner = player;

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_USER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.VIEW_MODE);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE_WIN);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, oppo);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR_ATTR, Color.RED);

        assertEquals(Player.Status.ENDGAME, player.status);
        assertEquals(Player.Status.ENDGAME, oppo.status);
    }

    /**
     * Tests for during an active game
     * @throws Exception
     */
    @Test
    public void InGameTest() throws Exception {
        when(request.queryParams("opponent")).thenReturn("Oppo");
        Player oppo = new Player("Oppo");
        oppo.status = Player.Status.INGAME;
        player.status = Player.Status.INGAME;
        lobby.saveUser(oppo);

        PlayerLobby.newGame(oppo);
        game = PlayerLobby.getGame(oppo.name);
        oppo.startGame(game);
        game.addRedPlayer(player);
        player.startGame(game);
        PlayerLobby.addGame(player, game);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetGameRoute.CURRENT_USER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE_ATTR, GetGameRoute.VIEW_MODE);
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE_ATTR, GetGameRoute.TITLE_YOUR_TURN);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER_ATTR, oppo);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(GetGameRoute.ACTIVE_COLOR_ATTR, Color.RED);

        assertEquals(Player.Status.INGAME, player.status);
        assertEquals(Player.Status.INGAME, oppo.status);
    }





}
