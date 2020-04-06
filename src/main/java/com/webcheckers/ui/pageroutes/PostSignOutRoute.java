package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.WebServer;
import spark.*;
import com.webcheckers.appl.Player;

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
    private boolean is_removed;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param playerLobby
     * @param templateEngine
     */
    public PostSignOutRoute( PlayerLobby playerLobby, TemplateEngine templateEngine){
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        LOG.config("PostSignOutRoute is initialized");
    }

    public boolean getIsRemoved(){
        return is_removed;
    }

    //
    // TemplateViewRoute method
    //
    @Override
    public Object handle(Request request, Response response) throws Exception{
        LOG.fine("PostSignOutRoute invoked");
        final Session httpSession = request.session();

        Player currentPlayer = httpSession.attribute(CURRENT_USER_ATTR);
        is_removed = playerLobby.removeUser(currentPlayer);
        if(is_removed){
            PlayerLobby.decrement();
        }
        else{
            System.out.println("User wasn't online");
            System.out.println(currentPlayer.getName());

        }
        currentPlayer.status = Player.Status.OFFLINE;
        httpSession.attribute(CURRENT_USER_ATTR, null);

        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
