package org.simplecrud.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Tag;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionDto {
    private long id;
    private String content;
    List<Answer> answers;
    private List<Tag> tags;
}
