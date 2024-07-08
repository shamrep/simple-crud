package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;

public class DeleteQuestionHandler implements Handler {

    @Override
    public Response handle(Request request) {
        return Response.noContent();
    }
}
