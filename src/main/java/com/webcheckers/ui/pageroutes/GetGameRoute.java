package com.webcheckers.ui.pageroutes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.model.Color;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

import static spark.Spark.halt;


public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private static final Message GAME_MSG = Message.info("Game Page.");

    private static final Message GAMEID_MSG = Message.info("gameID");

    private static final Message CURRENT_USER_MSG = Message.info("currentUser");

    private static final Message VIEWMODE_MSG = Message.info("modeOptions");

    private static final Message RED_PLAYER_MSG = Message.info("redPlayer");

    private static final Message WHITE_PLAYER_MSG = Message.info("whitePlayer");

    private static final Message ACTIVE_COLOR_MSG = Message.info("activeColor");

    // Values used in the view-model map for rendering the home view.
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Let's start the Game!";
    static final String GAMEID_ATTR = "gameID";
    static final String CURRENT_USER_ATTR = "currentUser";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String VIEW_MODE = "PLAY";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String ACTIVE_COLOR = "RED";
    static final String BOARD_ATTR = "board";

    //
    // Attributes
    //
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = Objects.requireNonNull(playerLobby, "PlayerLobby is required");
        //
        LOG.config("GetGameRoute is initialized.");

    }
    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        //Get myself, and my opponent
        Player me = request.session().attribute("currentUser");
        String opponent = request.queryParams("opponent");
        Game game;
        if(me.status == Player.Status.SEARCHING){
            game = PlayerLobby.getGame(opponent);
            game.addRedPlayer(me);
        }
        else{
            game = PlayerLobby.getGame(me.name);
        }
        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();
        redPlayer.status = Player.Status.INGAME;
        whitePlayer.status = Player.Status.INGAME;
        // Add Objects to the view
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        vm.put(GAMEID_ATTR, me.playerID);
        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(RED_PLAYER_ATTR, redPlayer);
        vm.put(WHITE_PLAYER_ATTR, whitePlayer);
        vm.put(ACTIVE_COLOR_ATTR, ACTIVE_COLOR);
        // Determine my POV
        if(me == redPlayer) {
            vm.put(BOARD_ATTR, new BoardView(Color.RED));
        }
        else{
            vm.put(BOARD_ATTR, new BoardView(Color.WHITE));
        }
        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
