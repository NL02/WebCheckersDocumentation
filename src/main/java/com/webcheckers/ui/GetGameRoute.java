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

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private static final Message GAME_MSG = Message.info("Game Page.");

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

    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Let's start the Game!");

        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }

}
