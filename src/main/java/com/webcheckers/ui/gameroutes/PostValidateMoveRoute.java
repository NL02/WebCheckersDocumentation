package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.ui.pageroutes.PostSignOutRoute;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PostValidateMoveRoute {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    static final String CURRENT_USER_ATTR = "currentUser";

    public PostValidateMoveRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    public Object handle(Request request, Response response) throws Exception{
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Move move = gson.fromJson(request.body(), Move.class);

        //String currUser = httpSession.attribute(PostSignOutRoute.CURRENT_USER_ATTR);
        //CheckersGame game = PlayerLobby.getGame(currUser);

        Message moveMessage = Message.info("valid");

        String json;
        json = gson.toJson(moveMessage);
        return json;
    }

}
