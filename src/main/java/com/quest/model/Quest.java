package com.quest.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Quest
{
    private long id;
    private String name;
    private List<Question> questions;
}
