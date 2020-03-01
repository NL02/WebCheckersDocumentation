package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;
import com.webcheckers.model.Player;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSignOutRoute implements Route{

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
    // Values to be used in the View Model
    static final String CURRENT_USER_ATTR = "currentUser";

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
        final Session httpSession = request.session();

        Player currentPlayer = httpSession.attribute(CURRENT_USER_ATTR);
        boolean is_removed = playerLobby.removeUser(currentPlayer);
        if(is_removed){
            PlayerLobby.decrement();
        }
        else{
            System.out.println("User wasn't online");
        }
        currentPlayer.status = Player.Status.OFFLINE;
        httpSession.attribute(CURRENT_USER_ATTR, null);

        response.redirect("/");
        return null;
    }
}
