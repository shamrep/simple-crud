package org.simplecrud.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.List;

@Getter
public class QuestionDto {

    private final Long id;
    private final String content;
    private final List<AnswerDto> answers;
    private final List<TagDto> tags;

    @JsonCreator
    public QuestionDto(
            @JsonProperty("id") Long id,
            @JsonProperty("content") String content,
            @JsonProperty("answers") List<AnswerDto> answers,
            @JsonProperty("tags") List<TagDto> tags) {

        this.id = id;
        this.content = content;
        this.answers = answers;
        this.tags = tags;
    }

    public static QuestionDto of(Question question) {
        List<TagDto> tagDtos = question.getTags().stream().map(TagDto::of).toList();
        List<AnswerDto> answerDtos = question.getAnswers().stream().map(AnswerDto::of).toList();

        return new QuestionDto(question.getId(), question.getContent(), answerDtos, tagDtos);
    }

    public Question toQuestion() {
        List<Answer> answers = this.answers.stream()
                .map(answerDto -> new Answer(answerDto.getId(), answerDto.getContent(), answerDto.isCorrect()))
                .toList();

        List<Tag> tags = this.tags.stream()
                .map(tagDto -> new Tag(tagDto.getId(), null))
                .toList();

        return new Question(id, content, answers, tags);
    }

}
