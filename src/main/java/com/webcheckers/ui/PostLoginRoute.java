package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostLoginRoute implements Route {

    // Values used in the view-model map for rendering the post login view after a login attempt

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());
    private static final String USER_USER = "username";

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
        final Session httpSession = request.session();

        final String username = request.queryParams(USER_USER);

        // adds user to the map
        boolean is_added = playerLobby.saveUser(username);
        if(is_added){
            PlayerLobby.increment();
        }

        httpSession.attribute("currentUser", new Player(username));

        System.out.println(username);

        response.redirect("/");
        return null;
    }
}
