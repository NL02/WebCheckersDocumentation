package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.ui.board.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GetNewGameRoute implements Route {

    private static final Logger LOG = Logger.getLogger(GetNewGameRoute.class.getName());
    private final TemplateEngine templateEngine;

    GetNewGameRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetGameRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Let's start the Game!");
        vm.put("gameID", 0);
        vm.put("currentUser", request.session().attribute("currentUser"));
        vm.put("viewMode", "PLAY");
        vm.put("redPlayer", new Player("Waiting for Player"));
        vm.put("whitePlayer", request.session().attribute("currentUser"));
        vm.put("activeColor", "RED");
        vm.put("board", new BoardView());
        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
