package com.quest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralConstants {
    //Т.к. проект учебный, то папка для хранения json-файлов лежит в корне проекта
    public static final String QUEST_JSON_FOLDER = "C:\\Users\\ndpee\\IdeaProjects\\com.javarush.peev.quest\\quests";
    public static final Long FIRST_QUESTION_ID = 11L;
}
