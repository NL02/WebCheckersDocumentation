package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class  GetHomeRoute implements Route {

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
  static final String GAME_LIST_ATTR = "gameList";


  //
  // Attributes
  //

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
  public Object handle(Request request, Response response) throws Exception{
    LOG.fine("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);

    // retrieve the HTTP session
    final Session httpSession = request.session();

    // display a user message in the Home page
    vm.put(MESSAGE_ATTR, WELCOME_MSG);
    Message num_players = Message.info(String.format(NUM_PLAYERS_MSG, playerLobby.getLiveCount()));
    vm.put( NUM_PLAYERS_ATTR, num_players);

    // display navbar
    vm.put(CURRENT_USER_ATTR, httpSession.attribute("currentUser"));

    // list all logged-in players
    vm.put(PLAYER_LIST_ATTR, playerLobby.getWaitingPlayer());

    // list all active games
    vm.put(GAME_LIST_ATTR, playerLobby.getAllGames());


    Player currentUser = request.session().attribute("currentUser");
    if(currentUser != null && currentUser.status == Player.Status.INGAME){
      response.redirect("/game");
//      halt();
    }
    else if(currentUser != null && currentUser.status != Player.Status.SEARCHING) {
      currentUser.status = Player.Status.SEARCHING;
      PlayerLobby.removeGame(currentUser);
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
