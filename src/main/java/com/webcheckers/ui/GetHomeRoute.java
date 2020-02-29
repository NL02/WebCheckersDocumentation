package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // Values used in the view-model map for rendering the home view.
  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  static final String TITLE_ATTR = "title";
  static final String TITLE = "Welcome!";
  static final String NUM_PLAYERS_ATTR = "num_players";
  static final String NUM_PLAYERS_MSG = "There are %d players signed on.";
  static final String MESSAGE_ATTR = "message";
  static final String VIEW_NAME = "home.ftl";
  static final String NEW_PLAYER_ATTR = "newPlayer";
  static final String CURRENT_USER_ATTR = "currentUser";
  static final String PLAYER_LIST_ATTR = "playerList";

  // Key in the session attribute map for the player who started the session
  static final String PLAYERSERVICES_KEY = "playerServices";
  static final String TIMEOUT_SESSION_KEY = "timeoutWatchdog";

  // The length of the session timeout in seconds
  static final int SESSION_TIMEOUT_PERIOD = 120;

  //
  // Attributes

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    this.playerLobby = playerLobby;
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    // retrieve the HTTP session
    final Session httpSession = request.session();

    // display a user message in the Home page
    vm.put(MESSAGE_ATTR, WELCOME_MSG);
    Message num_players = Message.info(String.format(NUM_PLAYERS_MSG, playerLobby.getLiveCount())); //change 3 to numplayers from playerlobby
    vm.put( NUM_PLAYERS_ATTR, num_players);

    // display navbar
    vm.put(CURRENT_USER_ATTR, httpSession.attribute("currentUser"));

    // list all logged-in players
    vm.put(PLAYER_LIST_ATTR, playerLobby.getWaitingPlayer());

    // if this is a brand new browser session or a session that timed out
    if(httpSession.attribute(PLAYERSERVICES_KEY) == null) {
      // get the object that will provide client-specific services for this player
      final PlayerServices playerService = playerLobby.newPlayerServices();
      httpSession.attribute(PLAYERSERVICES_KEY, playerService);

      // setup session timeout. The valueUnbound() method in the SessionTimeoutWatchdog will
      // be called when the session is invalidated. The next invocation of this route will
      // have a new Session object with no attributes.
      httpSession.attribute(TIMEOUT_SESSION_KEY, new SessionTimeoutWatchdog(playerService));
      httpSession.maxInactiveInterval(SESSION_TIMEOUT_PERIOD);

      // render the Game Form view
      vm.put(NEW_PLAYER_ATTR, true);

      return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
//    else {
//      // there is a game already being played so redirect the user to the Game view
//      response.redirect(WebServer.GAME_URL);
//      halt();
//      return null;
//    }

    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
