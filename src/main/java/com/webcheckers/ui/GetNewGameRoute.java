package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import com.webcheckers.ui.board.Color;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetNewGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetNewGameRoute.class.getName());

    // Values used in the view-model map for rendering the home view.
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Waiting for Opponent";
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

    GetNewGameRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetNewGameRoute is invoked.");
        Player currentPlayer = request.session().attribute("currentUser");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        vm.put(GAMEID_ATTR, 0);
        vm.put(CURRENT_USER_ATTR, currentPlayer);
        vm.put(VIEW_MODE_ATTR, VIEW_MODE);
        vm.put(RED_PLAYER_ATTR, new Player("Waiting for Player"));
        vm.put(WHITE_PLAYER_ATTR, currentPlayer);
        vm.put(ACTIVE_COLOR_ATTR, ACTIVE_COLOR);
        vm.put(BOARD_ATTR, new BoardView(Color.WHITE));
        currentPlayer.status = Player.Status.WAITING;
        // render the View
        //TODO: SET PLAYER TO SEARCHING IF THEY GO TO HOME PAGE
        //TODO: FIGURE OUT HOW TO SEND A PLAYER TO /game FROM /newgame
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
