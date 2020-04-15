package com.webcheckers.ui.pageroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;
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
    static final String BOARD_ATTR = "board";
    static final String IS_GAME_OVER = "isGameOver";
    static final String GAME_OVER_ATTR = "gameOverMessage";
    static final String MODE_OPTION = "modeOptionsAsJSON";

    //
    // Attributes
    //
    private final TemplateEngine templateEngine;
    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
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

        //Get myself, and my opponent
        Player me = request.session().attribute("currentUser");

        String opponent = request.queryParams("opponent");

        if(me.status == Player.Status.SEARCHING){
            return handleNewGame(request, response, opponent);
        }

        Game game = me.getGame();
        game.checkEndGame();
        if(me.status == Player.Status.ENDGAME || me.game.isGameOver() != null){
            return handleEndGame(request, response);
        }

        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();

        Map<String, Object> vm = new HashMap<>();

        if(game.getActivePlayer() == me){
            vm.put(TITLE_ATTR, TITLE_YOUR_TURN);
        }
        else{
            vm.put(TITLE_ATTR, TITLE_OPP_TURN);
        }

        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(RED_PLAYER_ATTR, redPlayer);
        vm.put(WHITE_PLAYER_ATTR, whitePlayer);
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        vm.put(BOARD_ATTR, new BoardView(me, game));

        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

    private Object handleNewGame(Request request, Response response, String opponent) throws Exception{
        if(opponent == null){
            return handleNewGame(request, response);
        }

        Player me = request.session().attribute(CURRENT_USER_ATTR);
        Game game = PlayerLobby.getGame(opponent);
        me.startGame(game);
        game.addRedPlayer(me);

        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();
        redPlayer.status = Player.Status.INGAME;
        whitePlayer.status = Player.Status.INGAME;

        Map<String, Object> vm = new HashMap<>();

        vm.put(TITLE_ATTR, TITLE_YOUR_TURN);
        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(RED_PLAYER_ATTR, redPlayer);
        vm.put(WHITE_PLAYER_ATTR, whitePlayer);
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        vm.put(BOARD_ATTR, new BoardView(me, game));

        return templateEngine.render(new ModelAndView(vm, "/game.ftl"));

    }

    private Object handleNewGame(Request request, Response response) throws Exception {

        Player me = request.session().attribute(CURRENT_USER_ATTR);

        Player ghost = new Player("Waiting for Player");

        Map<String, Object> vm = new HashMap<>();

        PlayerLobby.newGame(me);
        Game game = PlayerLobby.getGame(me.name);
        me.startGame(game);
        game.addRedPlayer(ghost);
        me.status = Player.Status.WAITING;

        vm.put(TITLE_ATTR, TITLE_WAIT);
        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(RED_PLAYER_ATTR, ghost);
        vm.put(WHITE_PLAYER_ATTR, me);
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        vm.put(BOARD_ATTR, new BoardView(me, game));

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

    private Object handleEndGame(Request request, Response response) throws Exception {
        Gson gson = new Gson();

        Player me = request.session().attribute(CURRENT_USER_ATTR);
        Game game = me.getGame();

        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();

        redPlayer.status = Player.Status.ENDGAME;
        whitePlayer.status = Player.Status.ENDGAME;

        me.endGame(game.winner == me);

        Map<String, Object> vm = new HashMap<>();

        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(RED_PLAYER_ATTR, redPlayer);
        vm.put(WHITE_PLAYER_ATTR, whitePlayer);
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        vm.put(BOARD_ATTR, new BoardView(me, game));

        final Map<String, Object> options = new HashMap<>(2);
        options.put(IS_GAME_OVER, true);
        options.put(GAME_OVER_ATTR, game.isGameOver());
        vm.put(MODE_OPTION, gson.toJson(options));
        if (game.winner == me) {
            vm.put(TITLE_ATTR, TITLE_WIN);
        } else {
            vm.put(TITLE_ATTR, TITLE_LOST);
        }

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
