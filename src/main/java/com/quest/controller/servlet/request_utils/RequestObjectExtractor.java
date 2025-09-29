package com.quest.controller.servlet.request_utils;

import com.quest.model.Answer;
import com.quest.model.Quest;
import com.quest.model.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class RequestObjectExtractor
{
    private final RequestParamExtractor paramExtractor;
    private final RequestParser parser;

    public RequestObjectExtractor() {
        this.paramExtractor = new RequestParamExtractor();
        this.parser = new RequestParser();
    }


    public Question getCurrentQuestion(HttpServletRequest req, HttpServletResponse resp, Quest quest) {
        String questionParam = paramExtractor.getParam(req, resp, "currentQuestionId");
        if (questionParam == null) return null;

        Long questionId = parser.parse(questionParam, Long::parseLong);
        if (questionId == null) return null;

        return quest.getQuestions().get(questionId);
    }


    public Answer getAnswer(HttpServletRequest req, HttpServletResponse resp, Question question) {
        String indexParam = paramExtractor.getParam(req, resp, "answerIndex");
        if (indexParam == null) return null;

        Integer index = parser.parse(indexParam, Integer::parseInt);
        if (index == null) return null;

        return validateIndex(question, index);
    }

    private Answer validateIndex(Question question, int index) {
        List<Answer> answers = question.getAnswers();
        if (index < 0 || index >= answers.size()) {
            return null;
        }
        return answers.get(index);
    }
}
