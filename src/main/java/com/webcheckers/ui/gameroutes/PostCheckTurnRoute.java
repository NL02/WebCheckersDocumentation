package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    public PostCheckTurnRoute() {
        LOG.fine("PostCheckTurnRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostCheckTurnRoute invoked");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Session session = request.session();
        Player me = session.attribute("currentUser");
        Game game = me.getGame();
        if(game.getActivePlayer() == me){
            return gson.toJson(Message.info("true"));
        }
        return gson.toJson(Message.info("false"));

    }
}
