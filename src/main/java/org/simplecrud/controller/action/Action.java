package org.simplecrud.controller.action;

import org.simplecrud.controller.Req;
import org.simplecrud.controller.Resp;

import java.io.IOException;

public interface Action {
    void process(Req req, Resp resp) throws IOException;
}
