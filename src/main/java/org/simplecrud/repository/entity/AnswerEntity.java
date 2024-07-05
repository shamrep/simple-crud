package org.simplecrud.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerEntity {
    private long id;
    private String text;
    private boolean isCorrect;
}
