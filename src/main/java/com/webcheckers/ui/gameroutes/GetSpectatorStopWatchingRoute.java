package com.webcheckers.ui.gameroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * Route handler for when a spectator chooses to stop spectating.
 */
public class GetSpectatorStopWatchingRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSpectatorStopWatchingRoute.class.getName());

    /**
     * Constructs a new GetSpectatorStopWatchingRoute.
     */
    public GetSpectatorStopWatchingRoute() {
        LOG.config("GetSpectatorStopWatchingRoute initialized");
    }

    /**
     * Handles /spectator/stopWatching requests.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("GetSpectatorStopWatchingRoute invoked");

        Session session = request.session();
        Player me = session.attribute("currentUser");

        me.status = Player.Status.SEARCHING;
        response.redirect("/");
        halt();
        return Message.info("true");
    }
}
