package com.quest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer
{
    String content;
    private long currentQuestionId;
    private long nextQuestionId;
    private boolean isCorrect;
}
