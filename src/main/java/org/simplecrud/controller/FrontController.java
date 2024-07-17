package org.simplecrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.controller.handler.Handler;
import org.simplecrud.service.validator.ValidationException;

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

    private void doMethod(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        Route route = RouteRegister.getInstance()
                .findRoute(httpRequest)
                .orElseGet(this::notFoundRoute);

        Request request = new Request(httpRequest, route.getUrlTemplate());
        Response response = handle(route.getHandler(), request);

        httpResponse.setStatus(response.getStatusCode());
        setHeaders(httpResponse, response);
        writeBodyToOutputStream(response.getBody(), httpResponse);
    }

    private Response handle(Handler handler, Request request) {
        try {
            return handler.handle(request);
        } catch (ValidationException e) {
            return Response.badRequest(e.getErrors());
        } catch (Exception e) {
            return Response.internalServerError();
        }
    }

    private void setHeaders(HttpServletResponse httpResponse, Response response) {
        response.getHeaders().forEach((k, v) -> httpResponse.setHeader(k, v.toString()));
    }

    private void writeBodyToOutputStream(Object body, HttpServletResponse httpResponse) throws IOException {
        if (body == null) {
            return;
        }

        try (OutputStream os = httpResponse.getOutputStream()) {
            new ObjectMapper().writeValue(os, body);
        }
    }

    private Route notFoundRoute() {
        return new Route(null, null, request -> Response.notFound());
    }
}
