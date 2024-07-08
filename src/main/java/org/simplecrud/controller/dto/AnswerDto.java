package org.simplecrud.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.simplecrud.service.model.Answer;

@RequiredArgsConstructor
@Getter
public class AnswerDto {

    private final long id;
    private final String content;
    private final boolean isCorrect;

    public static AnswerDto of(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getContent(), answer.isCorrect());
    }

}
