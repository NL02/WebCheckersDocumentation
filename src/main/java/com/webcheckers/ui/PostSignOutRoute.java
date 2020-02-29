package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;
import com.webcheckers.model.Player;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSignOutRoute implements Route{

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

    //
    // Attributes
    //
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostSignOutRoute( PlayerLobby playerLobby, TemplateEngine templateEngine){
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        LOG.config("PostSignOutRoute is initialized");
    }

    //
    // TemplateViewRoute method
    //
    @Override
    public Object handle(Request request, Response response){
        // start building the View-Model
        final Session httpSession = request.session();

        Player currentPlayer = httpSession.attribute("currentUser");
        boolean is_removed = playerLobby.removeUser(currentPlayer.getName());
        if(is_removed){
            PlayerLobby.decrement();
        }
        else{
            System.out.println("User doesn't exist in map");
        }

        httpSession.attribute("currentUser", null);

        response.redirect("/");
        return null;
    }
}
