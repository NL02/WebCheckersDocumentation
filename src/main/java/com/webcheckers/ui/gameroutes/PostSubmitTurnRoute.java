package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostSubmitTurnRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostSubmitturnRoute is initialized.");
    }

    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Gson gson = new Gson();

        Player player = httpSession.attribute("currentUser");
        Message moveMessage = player.getGame().submitTurn();
        playerLobby.findPlayer(player.name).getGame().changeActiveColor();

        return gson.toJson(moveMessage);
    }
}
