package org.simplecrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.controller.handler.Handler;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/*")
public class FrontController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doMethod(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doMethod(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doMethod(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doMethod(req, resp);
    }

    private void doMethod(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Handler handler = HandlerRegister.getInstance()
                .findHandler(req)
                .orElseGet(this::notFoundHandler);

        Request request = new Request(req);
        Response response = handler.handle(request);

        res.setStatus(response.getStatusCode());
        res.setContentType("application/json");
        writeBodyToOutputStream(response.getBody(), res.getOutputStream());
    }

    private void writeBodyToOutputStream(Object body, OutputStream os) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(os, body);
    }

    private Handler notFoundHandler() {
        return request -> Response.notFound();
    }
}
