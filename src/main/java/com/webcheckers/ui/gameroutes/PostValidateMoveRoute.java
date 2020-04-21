package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    static final String CURRENT_USER_ATTR = "currentUser";

    public PostValidateMoveRoute() {

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostValidateMoveRoute invoked");
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Move move = gson.fromJson(request.queryParams("actionData"), Move.class);

        Player player = httpSession.attribute(CURRENT_USER_ATTR);
        Message moveMessage = player.getGame().validateMove(move);

        return gson.toJson(moveMessage);
    }

}
