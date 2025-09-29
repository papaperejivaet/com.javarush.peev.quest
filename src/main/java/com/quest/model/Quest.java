package com.quest.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Quest
{
    private Long id;
    private String name;
    private Map<Long, Question> questions;

    @JsonSetter("questions")
    public void setQuestionsFromJson(List<Question> questionsList) {
        if (questionsList != null) {
            this.questions = questionsList.stream()
                    .collect(Collectors.toMap(Question::getId, q -> q));
        }
    }

}
