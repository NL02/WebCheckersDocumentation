package com.webcheckers.ui.pageroutes;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.logging.Logger;


public class GetLoginRoute implements Route {

    // Values used in the view-model map for rendering the login view after signin button is pressed.
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Please Login";
    static final String VIEW_NAME = "signin.ftl";
    private static final Logger LOG = Logger.getLogger(GetLoginRoute.class.getName());
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        LOG.config("GetLoginRoute is initialized.");
    }

    /**
     *  Renders Login Page
     * @param request http request
     * @param response http response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("GetLoginRoute is invoked.");

        HashMap<String, Object> vm = new HashMap<>();

        // display login page title
        vm.put(TITLE_ATTR, TITLE);


        // render the view
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}
