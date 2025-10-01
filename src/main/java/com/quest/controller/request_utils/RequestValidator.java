package com.quest.controller.request_utils;

import com.quest.controller.ServletParam;
import com.quest.controller.TransferUtil;
import com.quest.exception.QuestNotFoundInCacheException;
import com.quest.model.Answer;
import com.quest.model.Quest;
import com.quest.model.Question;
import com.quest.repository.QuestRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RequestValidator
{

    private final QuestRepository repository;
    private final RequestObjectExtractor objectExtractor;
    private final RequestParser parser;
    RequestParamExtractor paramExtractor;

    public RequestValidator(QuestRepository repository, RequestParamExtractor paramExtractor, RequestParser parser)
    {
        this.repository = repository;
        this.paramExtractor = paramExtractor;
        this.parser = parser;
        this.objectExtractor = new RequestObjectExtractor();
    }

    public Quest validateAndGetQuest(HttpServletRequest req, HttpServletResponse resp)
    {
        String questParam = paramExtractor.getParam(req, resp, ServletParam.QUEST_ID);
        if (questParam == null)
            return null;

        Long questId = parser.parse(questParam, Long::parseLong);
        Quest quest = null;

        try
        {
            quest = repository.loadQuestById(questId);
        }
        catch (QuestNotFoundInCacheException _)
        {
            TransferUtil.redirectToHome(req, resp);
        }

        return quest;
    }


    public Answer validateAndGetAnswer(HttpServletRequest req, HttpServletResponse resp, Quest quest)
    {
        Question currentQuestion = objectExtractor.getCurrentQuestion(req, resp, quest);
        if (currentQuestion == null)
        {
            TransferUtil.redirectToHome(req, resp);
            return null;
        }
        Answer answer = objectExtractor.getAnswer(req, resp, currentQuestion);
        if (answer == null)
        {
            TransferUtil.redirectToHome(req, resp);
            return null;
        }
        return answer;
    }
}
