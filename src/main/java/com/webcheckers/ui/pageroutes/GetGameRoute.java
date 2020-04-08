package com.webcheckers.ui.pageroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;
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
    static final String TITLE_YOUR_TURN = "It's your turn!";
    static final String TITLE_OPP_TURN = "It's your opponents turn!";
    static final String TITLE_WIN = "Congratulations! You won!";
    static final String TITLE_LOST = "Too bad! Better luck next time!";
    static final String TITLE_WAIT = "Waiting for Opponent";
    static final String GAME_ID_ATTR = "gameID";
    static final String CURRENT_USER_ATTR = "currentUser";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String VIEW_MODE = "PLAY";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String ACTIVE_COLOR = "RED";
    static final String BOARD_ATTR = "board";
    static final String IS_GAME_OVER = "isGameOver";
    static final String GAME_OVER_ATTR = "gameOverMessage";
    static final String MODE_OPTION = "modeOptionsAsJSON";

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
        this.playerLobby = playerLobby;
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
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("GetGameRoute is invoked.");
        Player ghost = new Player("Waiting for Player");

        //Get myself, and my opponent
        Player me = request.session().attribute("currentUser");

        String opponent = request.queryParams("opponent");

        Game game = null;

        Map<String, Object> vm = new HashMap<>();

        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);

        if(me.status != Player.Status.WAITING && me.status != Player.Status.INGAME
                && opponent == null){
            me.status = Player.Status.WAITING;
            if(PlayerLobby.getGame(me.name) == null) {
                PlayerLobby.newGame(me);
                game = PlayerLobby.getGame(me.name);
                me.startGame(game);
                game.addRedPlayer(ghost);
                vm.put(TITLE_ATTR, TITLE_WAIT);
            }
        }
        else if(me.status == Player.Status.SEARCHING){
            game = PlayerLobby.getGame(opponent);
            game.addRedPlayer(me);
            me.startGame(game);
            playerLobby.addGame(me, game);
            vm.put(TITLE_ATTR, TITLE_YOUR_TURN);
        }
        else if (me.status == Player.Status.INGAME){
            game = PlayerLobby.getGame(me.name);
            vm.put(TITLE_ATTR, TITLE_OPP_TURN);
        }
        else if (me.status == Player.Status.WAITING){
            game = PlayerLobby.getGame(me.name);
        }

        if(me == game.getRedPlayer() && game.getActiveColor() == Color.RED){
            vm.put(TITLE_ATTR, TITLE_YOUR_TURN);
        }
        else if(me == game.getWhitePlayer() && game.getActiveColor() == Color.WHITE){
            vm.put(TITLE_ATTR, TITLE_YOUR_TURN);
        }
        else{
            vm.put(TITLE_ATTR, TITLE_OPP_TURN);
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
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());

        // Determine if the game is over
        if(game.isGameOver() != null){
            final Map<String, Object> options = new HashMap<>(2);
            options.put(IS_GAME_OVER, true);
            options.put(GAME_OVER_ATTR,game.isGameOver());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            vm.put(MODE_OPTION, gson.toJson(options));
            if(game.winner == me){
                vm.put(TITLE_ATTR, TITLE_WIN);
            }
            else{
                vm.put(TITLE_ATTR, TITLE_LOST);
            }

            // Add the scores to each players scorecard and remove the game from the playerLobby
            whitePlayer.endGame(game.winner == whitePlayer);
            redPlayer.endGame(game.winner == redPlayer);
            playerLobby.removeGame(game.getWhitePlayer());
            playerLobby.removeGame(game.getRedPlayer());
        }

        // Determine my POV

            vm.put(BOARD_ATTR, new BoardView(me, game));


        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
