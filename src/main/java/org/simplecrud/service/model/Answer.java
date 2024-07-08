package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {

    private Long id;
    private String content;
    private boolean isCorrect;
}
