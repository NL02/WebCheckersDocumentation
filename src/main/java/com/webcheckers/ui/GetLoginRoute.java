package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;


public class GetLoginRoute implements Route {

    //values used in the view-model map for rendering the signin view.
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Please Login";
    static final String VIEW_NAME = "signin.ftl";

    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());


    private final TemplateEngine templateEngine;


    public GetLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        LOG.config("GetLoginRoute is initialized.");
    }

    public Object handle(Request request, Response response) {
        LOG.finer("GetLoginRoute is invoked.");

        HashMap<String, Object> vm = new HashMap<>();

        // display login page title
        vm.put(TITLE_ATTR, TITLE);

        // render the view
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

}
