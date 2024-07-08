package org.simplecrud.controller.action;

import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.controller.Req;
import org.simplecrud.controller.Resp;

import java.io.IOException;

public class ResourceNotFoundAction implements Action {
    @Override
    public void process(Req req, Resp resp) throws IOException {
        resp.getResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getResponse().setContentType("application/json");
        resp.getResponse().getWriter().write("{\"error\": \"Resource not found\", \"code\": 404}");
        resp.getResponse().setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        resp.getResponse().setHeader("Pragma", "no-cache");
    }
}
