package org.simplecrud.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TagDto {

    private final long id;
    private final String name;

}
