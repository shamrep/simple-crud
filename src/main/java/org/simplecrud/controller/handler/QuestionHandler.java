package org.simplecrud.controller.handler;

import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.List;

public interface QuestionHandler extends Handler {
     default Question toQuestion(QuestionDto questionDto) {
        List<Answer> answers = questionDto.getAnswers().stream()
                .map(answerDto -> new Answer(
                        null,
                        answerDto.getContent(),
                        answerDto.isCorrect()))
                .toList();

        List<Tag> tags = questionDto.getTags().stream()
                .map(tagDto -> new Tag(
                        null,
                        tagDto.getName()))
                .toList();

        return new Question(null, questionDto.getContent(), answers, tags);
    }
}
