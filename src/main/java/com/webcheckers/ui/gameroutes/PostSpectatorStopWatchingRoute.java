package com.webcheckers.ui.gameroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostSpectatorStopWatchingRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSpectatorStopWatchingRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public PostSpectatorStopWatchingRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("PostSpectatorStopWatchingRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostSpectatorStopWatchingRoute invoked");

        Session session = request.session();
        Player me = session.attribute("currentUser");

        me.endGame(true);
        me.status = Player.Status.SEARCHING;
        response.redirect("/");
        halt();
        return Message.info("true");
    }
}
