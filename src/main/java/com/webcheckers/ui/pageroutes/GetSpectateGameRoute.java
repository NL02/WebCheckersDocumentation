package com.webcheckers.ui.pageroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.board.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetSpectateGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectateGameRoute.class.getName());

    // Values for rendering the Game view
    static final String TITLE_ATTR = "title";
    static final String TITLE_START = "Spectating a Game";
    static final String CURRENT_USER_ATTR = "currentUser";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String VIEW_MODE = "SPECTATOR";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String IS_GAME_OVER = "isGameOver";
    static final String GAME_OVER_ATTR = "gameOverMessage";
    static final String MODE_OPTION = "modeOptionsAsJSON";

    private final TemplateEngine templateEngine;

    public GetSpectateGameRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;

        LOG.config("GetSpectateGameRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("SpectateGameRoute invoked");

        String player = request.queryParams("player");
        Game game = PlayerLobby.getGame(player);

        Session session = request.session();
        Player me = session.attribute(CURRENT_USER_ATTR);
        me.startGame(game);

//        if(game == null){
//            response.redirect("/");
//            halt();
//        }
        Map<String, Object> vm = new HashMap<>();

        vm.put(CURRENT_USER_ATTR, me);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(TITLE_ATTR, TITLE_START);
        vm.put(RED_PLAYER_ATTR, game.getRedPlayer());
        vm.put(WHITE_PLAYER_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR_ATTR, game.getActiveColor());
        vm.put(BOARD_ATTR, new BoardView(me, game));

        if(game.isGameOver() != null) {
            final Map<String, Object> options = new HashMap<>(2);
            options.put(IS_GAME_OVER, true);
            options.put(GAME_OVER_ATTR, game.isGameOver());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            vm.put(MODE_OPTION, gson.toJson(options));
        }

        me.status = Player.Status.SPECTATING;
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
