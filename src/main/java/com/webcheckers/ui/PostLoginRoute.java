package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostLoginRoute implements Route {

    // Values used in the view-model map for rendering the post login view after a login attemp

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());

    private static final String USER_USER = "username";
    private static final String USER_PASS = "password";
    private static final String MESSAGE_ATTR = "message";

    private static final String ERROR_TYPE = "error";


    //
    // Attributes
    //
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostLoginRoute( PlayerLobby playerLobby, TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine,"templateEngine must not be null");

        this.playerLobby = playerLobby;
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
        final Session httpSession = request.session();


        ModelAndView mv;

        final String username = request.queryParams(USER_USER);
        final String password = request.queryParams(USER_PASS);

        // httpSession.attribute("currentUser", username);
        vm.put("currentUser", username);

        // adds user to the map
        boolean is_added = playerLobby.saveUser(username);

        System.out.println(username);

        response.redirect("/");
        return templateEngine.render(new ModelAndView(vm, "nav-bar.ftl"));
    }
}
