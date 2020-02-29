package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import com.webcheckers.appl.PlayerLobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostLoginRoute implements Route {

    // Values used in the view-model map for rendering the post login view after a login attemp

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());

    static final String USER_USER = "username";
    static final String MESSAGE_ATTR = "message";
    static final Message INVALIDUSERNAME = Message.info("Usernames must have at least one alphanumeric character");
    static final Message PICKANOTHERUSERNAME = Message.info("Another player is using that name, please pick another one");

    public enum AddUserStatus{
        INVLAID, // if the player picks a name that is less than one character or
        PICKANOTHER, // if the player picks a name that is already in use
        SUCCESS; // successfully picked a username
    }

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    public PostLoginRoute( PlayerLobby playerLobby, TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine,"templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        LOG.config("PostGuessRoute is initialized.");
    }

    //
    // TemplateViewRoute method
    //
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        final Session httpSession = request.session();

        final String username = request.queryParams(USER_USER);
        LOG.fine(username);

        Player newPlayer = new Player(username);

        AddUserStatus is_added = playerLobby.saveUser(newPlayer);

        if(is_added == AddUserStatus.SUCCESS){
            httpSession.attribute("currentUser", newPlayer);
        }
        else if (is_added == AddUserStatus.INVLAID){
            vm.put(MESSAGE_ATTR, INVALIDUSERNAME);
            response.redirect("/signin");
        }
        else{
            vm.put(MESSAGE_ATTR, PICKANOTHERUSERNAME);
            response.redirect("/signin");
        }

        response.redirect("/");
        return null;
    }
}
