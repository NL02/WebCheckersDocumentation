package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.*;

import com.webcheckers.appl.PlayerLobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Route handler for an attempt to login
 * @author Rayna Mishra
 */
public class PostLoginRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostLoginRoute.class.getName());

    // Values used in the view-model map for rendering the post login view after a login attempt
    static final String MESSAGE_ATTR = "message";
    static final String INVALID_USERNAME = "Usernames can only contain alphanumeric characters.";
    static final String PICK_ANOTHER_USERNAME = "%s is currently in use, please pick another.";
    static final String TITLE_ATTR = "title";
    static final String CURRENT_USER_ATTR = "currentUser";


    public enum AddUserStatus{
        INVLAID, // if the player picks a name that is less than one character or
        PICKANOTHER, // if the player picks a name that is already in use
        SUCCESS // successfully picked a username
    }

    //
    // Attributes
    //
    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param playerLobby The PlayeLobby instance of the server
     * @param templateEngine The rendering engine that will be used
     */
    public PostLoginRoute( PlayerLobby playerLobby, TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine,"templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        LOG.config("PostLoginRoute is initialized.");
    }

    /**
     * Handle method for PostLoginRoute
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("PostLoginRoute Invoked");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, "Bad Login");


        final Session httpSession = request.session();

        final String username = request.queryParams("username");
        LOG.fine(username + " has logged in");

        Player newPlayer;
        AddUserStatus is_added;

        if(playerLobby.findPlayer(username) == null) {
            newPlayer = new Player(username);
            is_added = playerLobby.saveUser(newPlayer);
        }
        else{
            newPlayer = playerLobby.findPlayer(username);
            if(newPlayer.status != Player.Status.OFFLINE){
                is_added = AddUserStatus.PICKANOTHER;
            }
            else {
                is_added = AddUserStatus.SUCCESS;
            }
        }

        if(is_added == AddUserStatus.SUCCESS){
            playerLobby.addOnlinePlayer(newPlayer);
            httpSession.attribute(CURRENT_USER_ATTR, newPlayer);
        }
        else if (is_added == AddUserStatus.INVLAID){
            Message error = Message.error(INVALID_USERNAME);
            vm.put(MESSAGE_ATTR, error);
            return templateEngine.render(new ModelAndView(vm, "/signin.ftl"));
        }
        else{
            Message error = Message.error(String.format(PICK_ANOTHER_USERNAME, username));
            vm.put(MESSAGE_ATTR, error);
            return templateEngine.render(new ModelAndView(vm, "/signin.ftl"));
        }

        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
