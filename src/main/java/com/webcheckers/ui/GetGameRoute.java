package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;


public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private static final Message GAME_MSG = Message.info("Game Page.");

    private static final Message GAMEID_MSG = Message.info("gameID");

    private static final Message CURRENT_USER_MSG = Message.info("currentUser");

    private static final Message VIEWMODE_MSG = Message.info("modeOptions");

    private static final Message RED_PLAYER_MSG = Message.info("redPlayer");

    private static final Message WHITE_PLAYER_MSG = Message.info("whitePlayer");

    private static final Message ACTIVE_COLOR_MSG = Message.info("activeColor");

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
        Player me = request.session().attribute("currentUser");
        //TODO: Get Opponent
        String opponent = request.queryParams("opponent");
        Player opp = playerLobby.findPlayer(opponent);
        System.out.println("Opponent's name is: " + opponent);
        Player playerSearching;
        Player playerWaiting;
        if(me.status == Player.Status.WAITING) {
            playerWaiting = me;
            playerSearching = opp;
        }
        else{
            playerSearching = me;
            playerWaiting = opp;
        }
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Let's start the Game!");
        vm.put("gameID", 0);
        vm.put("currentUser", me);
        vm.put("viewMode", "PLAY");
        vm.put("redPlayer", playerSearching);
        vm.put("whitePlayer", playerWaiting);
        vm.put("activeColor", "RED");
        vm.put("board", new BoardView());
        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

}
