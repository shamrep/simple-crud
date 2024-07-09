package org.simplecrud.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionEntity {
    private Long id;
    private String content;
}
