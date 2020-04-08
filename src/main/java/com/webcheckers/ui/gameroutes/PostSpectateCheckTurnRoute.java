package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

public class PostSpectateCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;
    private Color lastActiveColor = Color.RED;

    public PostSpectateCheckTurnRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("PostSpectateCheckTurnRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostSpectateCheckTurnRoute invoked");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
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