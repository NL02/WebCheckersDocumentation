package com.webcheckers.ui;


import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class GetLoginRoute implements Route {

    private final TemplateEngine templateEngine;

    public GetLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
    }

    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();


        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }

}
