package org.simplecrud.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Question {

    private Long id;
    private String content;
    private List<Answer> answers;
    private List<Tag> tags;
}
