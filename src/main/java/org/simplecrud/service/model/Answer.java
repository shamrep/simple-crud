package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplecrud.repository.entity.AnswerEntity;

@Getter
@AllArgsConstructor
public class Answer {
    private Long id;
    private String content;
    private boolean isCorrect;
    private long questionId;

    public static Answer of(AnswerEntity answerEntity) {
        return new Answer(answerEntity.getId(), answerEntity.getContent(), answerEntity.isCorrect(), answerEntity.getQuestionId() );
    }
}
