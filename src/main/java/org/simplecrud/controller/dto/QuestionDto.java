package org.simplecrud.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionDto {
    private long id;
    private String content;
}
