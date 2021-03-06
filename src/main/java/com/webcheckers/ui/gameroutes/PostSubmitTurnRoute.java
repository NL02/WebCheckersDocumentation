package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    public PostSubmitTurnRoute() {

        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostSubmitTurnRoute invoked");
        final Session httpSession = request.session();
        Gson gson = new Gson();

        Player player = httpSession.attribute("currentUser");
        Message moveMessage = player.getGame().submitTurn();
        if (moveMessage.getType() != Message.Type.ERROR) {
            player.getGame().changeActiveColor();
        }

        return gson.toJson(moveMessage);
    }
}
