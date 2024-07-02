package org.simplecrud.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {
    private long id;
    private String text;
    private List<String> answers;
    private List<Tag> tags;
    private int correctAnswer;
}
