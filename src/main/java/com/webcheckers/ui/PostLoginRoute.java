package com.webcheckers.ui;

import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.appl.PlayerLobby;
import java.util.Objects;
import java.util.logging.Logger;

public class PostLoginRoute implements Route {

    // Values used in the view-model map for rendering the post login view after a login attemp

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());

    static final String USER_USER = "username";

    static final String ERROR_TYPE = "error";

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

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
        final Session httpSession = request.session();


        final String username = request.queryParams(USER_USER);
        System.out.println(username);

        boolean is_added = playerLobby.saveUser(username);
        if(is_added){
            PlayerLobby.increment();
            httpSession.attribute("currentUser", new Player(username));
        }
        else{
            System.out.println("User not added");
        }


        response.redirect("/");
        return null;
    }
}
