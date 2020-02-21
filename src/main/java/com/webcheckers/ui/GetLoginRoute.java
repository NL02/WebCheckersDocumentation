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

    private static final Logger LOG = Logger.getLogger(GetLoginRoute.class.getName());

    private static final String LOGIN_MSG = "Please Login";

    private final TemplateEngine templateEngine;

    public GetLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        LOG.config("GetLoginRoute is initialized.");
    }

    public Object handle(Request request, Response response) {
        LOG.finer("GetLoginRoute is invoked.");

        HashMap<String, Object> vm = new HashMap<>();

        // display login page title
        vm.put("title", LOGIN_MSG);

        // render the view
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }

}
