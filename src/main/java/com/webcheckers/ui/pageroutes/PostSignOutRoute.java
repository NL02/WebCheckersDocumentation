package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostSignOutRoute implements Route{

    private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());
    // Values to be used in the View Model
    static final String CURRENT_USER_ATTR = "currentUser";
    private final String GAME_OVER_MSG = "%s has resigned from the game";

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param playerLobby PlayerLobby instance for the web server
     */
    public PostSignOutRoute(PlayerLobby playerLobby){

        this.playerLobby = playerLobby;
        LOG.config("PostSignOutRoute is initialized");
    }
    //
    // TemplateViewRoute method
    //
    @Override
    public Object handle(Request request, Response response) throws Exception{
        LOG.fine("PostSignOutRoute invoked");
        final Session httpSession = request.session();

        Player currentPlayer = httpSession.attribute(CURRENT_USER_ATTR);

        if(currentPlayer.status == Player.Status.INGAME){
            Game game = currentPlayer.getGame();
            game.gameOver(String.format(GAME_OVER_MSG, currentPlayer.name), game.getRedPlayer() == currentPlayer ? game.getWhitePlayer() : game.getRedPlayer());
        }

        playerLobby.endSession(currentPlayer);
        httpSession.attribute(CURRENT_USER_ATTR, null);

        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
