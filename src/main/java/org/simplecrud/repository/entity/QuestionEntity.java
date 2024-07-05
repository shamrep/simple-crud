package org.simplecrud.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionEntity {
    private long id;
    private String text;
}
