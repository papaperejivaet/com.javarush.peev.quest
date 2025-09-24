package com.quest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question
{
    private long id;
    private String title;
    private String content;
    private List<Answer> answers;
}
