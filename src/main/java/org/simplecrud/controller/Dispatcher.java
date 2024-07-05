package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    Map<Route, Action> actionMap = new HashMap<>();

    public Dispatcher() {
        this.actionMap.put(new Route("hello", Route.HttpMethod.GET), new GetQuestionAction());
    }

    public void dispatch(ReqResp reqResp) {
        actionMap.get(getRoute(reqResp)).process();
    }

    private Route getRoute(ReqResp reqResp) {
        HttpServletRequest request = reqResp.getRequest();

        String path = request.getPathInfo();
        String[] splitPath = path.split("/");

        String method = request.getMethod();

        return new Route(splitPath[1], Route.HttpMethod.valueOf(method));
    }
}
