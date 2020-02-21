package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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


    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine) {
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
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Let's start the Game!");

        // render the View
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

}
