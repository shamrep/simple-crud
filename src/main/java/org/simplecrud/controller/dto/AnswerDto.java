package org.simplecrud.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AnswerDto {

    private final long id;
    private final String content;
    private final boolean isCorrect;

}
