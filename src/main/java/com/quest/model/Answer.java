package com.quest.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer
{
    String content;
    private long currentQuestionId;
    private long nextQuestionId;
    @JsonProperty("isCorrect")
    private boolean isCorrect;
}
