package com.webcheckers.ui;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;


public class GetLoginRoute implements Route {

    private final TemplateEngine templateEngine;

    public GetLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public Object handle(Request request, Response response) {
        return null;
    }

}
