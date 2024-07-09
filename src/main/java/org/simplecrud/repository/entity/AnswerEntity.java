package org.simplecrud.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AnswerEntity {
    private long id;
    private String content;
    private boolean isCorrect;
    private long questionId;
}
