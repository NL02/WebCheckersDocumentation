package com.webcheckers.ui.gameroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetSpectatorStopWatchingRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectatorStopWatchingRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public GetSpectatorStopWatchingRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;

        LOG.config("GetSpectatorStopWatchingRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("GetSpectatorStopWatchingRoute invoked");

        Session session = request.session();
        Player me = session.attribute("currentUser");

        me.endGame(true);
        me.status = Player.Status.SEARCHING;
        response.redirect("/");
        halt();
        return Message.info("true");
    }
}