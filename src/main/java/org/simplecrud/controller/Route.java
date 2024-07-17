package org.simplecrud.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.simplecrud.controller.handler.Handler;

@RequiredArgsConstructor
@Getter
public class Route {

    private final String httpMethod;
    private final String urlTemplate;
    private final Handler handler;
}
