package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostLoginRoute implements Route {

    // Values used in the view-model map for rendering the post login view after a login attemp

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());

    static final String USER_USER = "username";
    static final String USER_PASS = "password";
    static final String MESSAGE_ATTR = "message";

    static final String ERROR_TYPE = "error";

    //
    // Attributes
    //
    private final TemplateEngine templateEngine;

    public PostLoginRoute( TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine,"templateEngine must not be null");

        this.templateEngine = templateEngine;
        LOG.config("PostGuessRoute is initialized.");
    }

    //
    // TemplateViewRoute method
    //
    @Override
    public Object handle(Request request, Response response) {
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();


        ModelAndView mv;

        final String username = request.queryParams(USER_USER);
        final String password = request.queryParams(USER_PASS);
        System.out.println(username);
        System.out.println(password);

        response.redirect("/");
        return null;
    }
}
