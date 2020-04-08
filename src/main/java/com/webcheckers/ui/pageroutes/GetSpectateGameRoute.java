package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class GetSpectateGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectateGameRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public GetSpectateGameRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("GetSpectateGameRoute is initialized");
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
