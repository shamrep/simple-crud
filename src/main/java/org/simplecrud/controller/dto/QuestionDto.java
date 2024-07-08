package org.simplecrud.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionDto {

    private final long id;
    private final String content;
    private final List<AnswerDto> answers;
    private final List<TagDto> tags;

}
