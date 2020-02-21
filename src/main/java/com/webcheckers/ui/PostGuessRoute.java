package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PostGuessRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostGuessRoute.class.getName());

    private static final String USER_USER = "username";
    private static final String USER_PASS = "password";

    private final TemplateEngine templateEngine;

    public PostGuessRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        LOG.config("PostGuessRoute is initialized.");
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
