package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final String GAME_OVER_MSG = "%s has resigned from the game";

    public PostResignGameRoute() {

        LOG.config("PostResignGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception{
        LOG.fine("PostResignRoute invoked");
        Player me = request.session().attribute("currentUser");


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        if(me.status != Player.Status.INGAME){
            return gson.toJson(Message.error("You can't resign if you haven't started a game!"));
        }
        Game game = me.getGame();
        game.gameOver(String.format(GAME_OVER_MSG, me.name), game.getRedPlayer() == me ? game.getWhitePlayer() : game.getRedPlayer());

        return gson.toJson(Message.info("true"));
    }

}
