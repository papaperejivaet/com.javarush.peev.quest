package com.quest.controller.servlet;

import com.quest.controller.ScoreManager;
import com.quest.controller.ServletParam;
import com.quest.controller.TransferAddress;
import com.quest.controller.TransferUtil;
import com.quest.controller.request_utils.RequestObjectExtractor;
import com.quest.controller.request_utils.RequestParser;
import com.quest.controller.request_utils.RequestValidator;
import com.quest.model.Answer;
import com.quest.model.Quest;
import com.quest.model.Question;
import com.quest.repository.QuestRepository;
import com.quest.util.GeneralConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LogicServlet", value = "/quest")
public class LogicServlet extends HttpServlet
{
    private transient RequestValidator validator;

    @Override
    public void init() {
        validator = new RequestValidator(QuestRepository.getInstance(),
                 new RequestObjectExtractor(),
                new RequestParser());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = validator.validateAndGetQuest(req, resp);
        if (quest == null) return;

        req.setAttribute(ServletParam.QUEST, quest);

        Question firstQuestion = quest.getQuestions().get(GeneralConstants.FIRST_QUESTION_ID);
        req.setAttribute(ServletParam.QUESTION, firstQuestion);

        ScoreManager.resetScore(req.getSession());

        TransferUtil.forward(req, resp, TransferAddress.QUEST_PAGE);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = validator.validateAndGetQuest(req, resp);
        if (quest == null) return;

        req.setAttribute(ServletParam.QUEST, quest);

        Answer selectedAnswer = validator.validateAndGetAnswer(req, resp, quest);
        if (selectedAnswer == null) return;
        ScoreManager.addPoints(req.getSession(), selectedAnswer);

        int currentScore = ScoreManager.getScore(req.getSession());
        req.setAttribute(ServletParam.SCORE, currentScore);

        long nextQuestionId = selectedAnswer.getNextQuestionId();
        Question nextQuestion = quest.getQuestions().get(nextQuestionId);

        if (nextQuestion != null) {
            req.setAttribute(ServletParam.QUESTION, nextQuestion);
            TransferUtil.forwardToCorrectPage(req, resp, nextQuestion);
        } else {
            TransferUtil.forward(req, resp, TransferAddress.FINAL_PAGE);
        }
    }


}
