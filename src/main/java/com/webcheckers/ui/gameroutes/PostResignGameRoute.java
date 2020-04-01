package com.webcheckers.ui.gameroutes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.logging.Logger;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final String ERR_MSSG = "Failed resign";
    private final String PASS_MSSG = "Passed resign";

    private Message message;

    public PostResignGameRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostResignGameRoute is initialized.");
    }

    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player currUser = httpSession.attribute("currentUser");

        message = Message.info(PASS_MSSG);

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson(message);

        return json;
    }

}
