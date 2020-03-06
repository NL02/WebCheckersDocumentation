package com.webcheckers.ui.gameroutes;

import com.webcheckers.appl.PlayerLobby;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PostSubmitTurnRoute {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostSubmitTurnRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostSubmitturnRoute is initialized.");
    }

}
