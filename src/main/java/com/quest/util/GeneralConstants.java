package com.quest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneralConstants {
    //Т.к. проект учебный, то папка для хранения json-файлов лежит в корне проекта
    public static final String QUEST_JSON_FOLDER = "";

    public static final Long FIRST_QUESTION_ID = 11L;
    public static final int POINTS_PER_CORRECT_ANSWER = 25;

}
