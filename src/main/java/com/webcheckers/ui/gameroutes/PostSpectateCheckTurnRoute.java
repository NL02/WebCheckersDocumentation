package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostSpectateCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private Color lastActiveColor = Color.RED;

    public PostSpectateCheckTurnRoute() {

        LOG.config("PostSpectateCheckTurnRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostSpectateCheckTurnRoute invoked");
        Gson gson = new Gson();
        Session session = request.session();
        Player me = session.attribute("currentUser");
        Game game = me.getGame();
        if(game.getActiveColor() != lastActiveColor){
            if(lastActiveColor == Color.RED){
                lastActiveColor = Color.WHITE;
            }
            else{
                lastActiveColor = Color.RED;
            }
            return gson.toJson(Message.info("true"));
        }
        else{
            return gson.toJson(Message.info("false"));
        }

    }
}