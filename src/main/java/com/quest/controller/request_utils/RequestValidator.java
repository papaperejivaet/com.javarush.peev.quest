package com.quest.controller.request_utils;

import com.quest.controller.ServletParam;
import com.quest.controller.TransferUtil;
import com.quest.model.Answer;
import com.quest.model.Quest;
import com.quest.model.Question;
import com.quest.repository.QuestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RequestValidator
{

    private final QuestRepository repository;
    private final RequestObjectExtractor extractor;
    private final RequestParser parser;

    public RequestValidator(QuestRepository repository, RequestObjectExtractor extractor, RequestParser parser) {
        this.repository = repository;
        this.extractor = extractor;
        this.parser = parser;
    }

    public Quest validateAndGetQuest(HttpServletRequest req, HttpServletResponse resp) {
        RequestParamExtractor paramExtractor = new RequestParamExtractor();
        String questParam = paramExtractor.getParam(req, resp, ServletParam.QUEST_ID);
        if (questParam == null) return null;

        Long questId = parser.parse(questParam, Long::parseLong);
        Quest quest = repository.loadQuestById(questId);
        if (quest == null) {
            TransferUtil.redirectToHome(req, resp);
            return null;
        }
        return quest;
    }

    public boolean hasCurrentQuestion(HttpServletRequest req) {
        return req.getParameter("currentQuestionId") != null;
    }

    public Answer validateAndGetAnswer(HttpServletRequest req, HttpServletResponse resp, Quest quest) {
        Question currentQuestion = extractor.getCurrentQuestion(req, resp, quest);
        if (currentQuestion == null)
        {
            TransferUtil.redirectToHome(req, resp);
            return null;
        }
        Answer answer = extractor.getAnswer(req, resp, currentQuestion);
        if (answer == null)
        {
            TransferUtil.redirectToHome(req, resp);
            return null;
        }
        return answer;
    }
}
