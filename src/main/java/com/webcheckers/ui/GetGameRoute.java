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

    public GetGameRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public Object handle(Request request, Response response) {
        return null;
    }

}
