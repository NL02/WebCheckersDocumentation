package com.webcheckers.ui.pageroutes;

import com.google.gson.internal.$Gson$Preconditions;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

public class PostResignRoute {

    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());
    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    public PostResignRoute( PlayerLobby playerLobby, TemplateEngine templateEngine){

        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        LOG.config("PostResignRoute is initialized");
    }

    @Override
    public Object handle(Request request, Response response){

    }

}
