package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;

public interface Handler {
    Response handle(Request request);
}
