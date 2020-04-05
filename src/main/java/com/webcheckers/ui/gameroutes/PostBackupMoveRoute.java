package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import spark.*;

import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostBackupMoveRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostBackupMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Player player = request.session().attribute("currentUser");
        Game game = player.getGame();
        Gson gson = new Gson();

        return gson.toJson(game.backupMove());
    }

}
