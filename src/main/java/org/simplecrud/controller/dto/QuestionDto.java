package org.simplecrud.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplecrud.service.model.Question;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionDto {

    private final long id;
    private final String content;
    private final List<AnswerDto> answers;
    private final List<TagDto> tags;

    public static QuestionDto of(Question question) {
        List<TagDto> tagDtos = question.getTags().stream().map(TagDto::of).toList();
        List<AnswerDto> answerDtos = question.getAnswers().stream().map(AnswerDto::of).toList();

        return new QuestionDto(question.getId(), question.getContent(), answerDtos, tagDtos);
    }

}
