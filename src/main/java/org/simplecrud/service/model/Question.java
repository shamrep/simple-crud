package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Question {
    private long id;
    private String content;
    private List<Answer> answers;
    private List<Tag> tags;
}
