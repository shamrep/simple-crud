package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {
    private long id;
    private String content;
    private boolean isCorrect;
}
