package org.simplecrud.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    private long id;
    private long text;
    private boolean isCorrect;
}
