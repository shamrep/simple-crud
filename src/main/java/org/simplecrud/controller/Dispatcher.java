package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.simplecrud.controller.action.Action;
import org.simplecrud.controller.action.GetQuestionAction;
import org.simplecrud.controller.action.ResourceNotFoundAction;
import org.simplecrud.controller.action.SaveQuestion;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    Map<Route, Action> actionMap = new HashMap<>();

    public Dispatcher() {
        this.actionMap.put(new Route("question", Route.HttpMethod.GET), new GetQuestionAction());
        this.actionMap.put(new Route("question", Route.HttpMethod.POST), new SaveQuestion());
    }

    public void dispatch(Req req, Resp resp) {
        try {
            Action action = actionMap.get(getRoute(req, resp));

            if(action == null) {
              action = new ResourceNotFoundAction();
            }

            action.process(req, resp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Route getRoute(Req req, Resp resp) {
        HttpServletRequest request = req.getRequest();

        String path = request.getPathInfo();
        String[] splitPath = path.split("/");

        String method = request.getMethod();

        return new Route(splitPath[1], Route.HttpMethod.valueOf(method));
    }
}
