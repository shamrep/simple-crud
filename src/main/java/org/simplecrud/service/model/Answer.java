package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplecrud.repository.entity.AnswerEntity;

@Getter
@AllArgsConstructor
public class Answer {

    private Long id;
    private String content;
    private boolean correct;

    public static Answer of(AnswerEntity entity) {
        return new Answer(entity.getId(), entity.getContent(), entity.isCorrect());
    }
}
