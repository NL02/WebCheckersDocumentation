package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostLoginRoute implements Route {

    // Values used in the view-model map for rendering the post login view after a login attemp

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());

    private static final String USER_USER = "username";
    private static final String USER_PASS = "password";

    private final TemplateEngine templateEngine;

    public PostLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        LOG.config("PostGuessRoute is initialized.");
    }


    @Override
    public Object handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        return null;
    }
}
