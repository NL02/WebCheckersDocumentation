package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.board.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;


public class GetGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    // Values used in the view-model map for rendering the home view.
    static final String TITLE_ATTR = "title";
    static final String TITLE_START = "Let's start the Game!";
    static final String TITLE_WAIT = "Waiting for Opponent";
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
        Player ghost = new Player("Waiting for Player");

        //Get myself, and my opponent
        Player me = request.session().attribute("currentUser");

        String opponent = request.queryParams("opponent");

        Game game = null;

//        System.out.println(me.status.toString() + "BRUhhHHH");
//        System.out.println("THIS IS THE OPPONENT " + opponent);

        Map<String, Object> vm = new HashMap<>();

        vm.put(TITLE_ATTR, TITLE_WAIT);
//        vm.put(GAMEID_ATTR, me.playerID);
        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);

        if(me.status != Player.Status.WAITING && me.status != Player.Status.INGAME
                && opponent == null && me.status != Player.Status.RESIGN){
            me.status = Player.Status.WAITING;
            if(PlayerLobby.getGame(me.name) == null) {
                PlayerLobby.newGame(me);
                game = PlayerLobby.getGame(me.name);
                me.startGame(game);
                game.addRedPlayer(ghost);
            }
        }
        else if(me.status == Player.Status.SEARCHING){
            game = PlayerLobby.getGame(opponent);
            game.addRedPlayer(me);
            me.startGame(game);
            playerLobby.addGame(me, game);
            vm.put(TITLE_ATTR, TITLE_START);
        }
        else if (me.status == Player.Status.INGAME){
            game = PlayerLobby.getGame(me.name);
            vm.put(TITLE_ATTR, TITLE_START);
        }
        else if (me.status == Player.Status.WAITING){
            game = PlayerLobby.getGame(me.name);
        }
        else if(me.status == Player.Status.RESIGN){
            response.redirect(WebServer.HOME_URL);
            return null;
        }


        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();
        if(!redPlayer.equals(ghost)) {
            redPlayer.status = Player.Status.INGAME;
            whitePlayer.status = Player.Status.INGAME;
        }

        // Add Objects to the view
        vm.put(RED_PLAYER_ATTR, redPlayer);
        vm.put(WHITE_PLAYER_ATTR, whitePlayer);
        vm.put(ACTIVE_COLOR_ATTR, playerLobby.findPlayer(whitePlayer.name).getGame().getActiveColor());

        // Determine my POV
        if(me == redPlayer) {
            vm.put(BOARD_ATTR, new BoardView(me, playerLobby.findPlayer(me.name).getGame()));
        }
        else{
            vm.put(BOARD_ATTR, new BoardView(whitePlayer, playerLobby.findPlayer(whitePlayer.name).getGame()));
        }
        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
