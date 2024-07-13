package org.simplecrud.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.simplecrud.service.model.Answer;

@Getter
public class AnswerDto {

    private final Long id;
    private final String content;
    private final boolean correct;
    private final Long questionId;

    @JsonCreator
    public AnswerDto(
            @JsonProperty("id") Long id,
            @JsonProperty("content") String content,
            @JsonProperty("correct")  boolean correct,
            @JsonProperty("questionId") Long questionId) {

        this.id = id;
        this.content = content;
        this.correct = correct;
        this.questionId = questionId;
    }

    public static AnswerDto of(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getContent(), answer.isCorrect(), answer.getQuestionId());
    }

    public static Answer toAnswer(AnswerDto answerDto) {
        return new Answer(answerDto.getId(), answerDto.getContent(), answerDto.isCorrect(), answerDto.questionId);
    }

}
