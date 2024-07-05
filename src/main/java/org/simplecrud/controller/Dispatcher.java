package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.simplecrud.controller.action.Action;
import org.simplecrud.controller.action.GetQuestionAction;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    Map<Route, Action> actionMap = new HashMap<>();

    public Dispatcher() {
        this.actionMap.put(new Route("question", Route.HttpMethod.GET), new GetQuestionAction());
    }

    public void dispatch(ReqResp reqResp) {
        actionMap.get(getRoute(reqResp)).process(reqResp);
    }

    private Route getRoute(ReqResp reqResp) {
        HttpServletRequest request = reqResp.getRequest();

        String path = request.getPathInfo();
        String[] splitPath = path.split("/");

        String method = request.getMethod();

        return new Route(splitPath[1], Route.HttpMethod.valueOf(method));
    }
}
