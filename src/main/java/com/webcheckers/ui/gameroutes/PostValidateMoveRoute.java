package com.webcheckers.ui.gameroutes;

import com.webcheckers.appl.PlayerLobby;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PostValidateMoveRoute {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostValidateMoveRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostValidateMoveRoute is initialized.");
    }

}
