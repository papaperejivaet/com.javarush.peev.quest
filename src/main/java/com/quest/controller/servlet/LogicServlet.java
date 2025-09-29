package com.quest.controller.servlet;

import com.quest.controller.TransferAddress;
import com.quest.controller.TransferUtil;
import com.quest.controller.servlet.request_utils.RequestObjectExtractor;
import com.quest.controller.servlet.request_utils.RequestValidator;
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
        QuestRepository repository = QuestRepository.getInstance();
        RequestObjectExtractor extractor = new RequestObjectExtractor();
        validator = new RequestValidator(repository, extractor);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = validator.validateAndGetQuest(req, resp);
        if (quest == null) return;

        req.setAttribute("quest", quest);

        Question firstQuestion = quest.getQuestions().get(GeneralConstants.FIRST_QUESTION_ID);
        req.setAttribute("question", firstQuestion);

        TransferUtil.forward(req, resp, TransferAddress.QUEST_PAGE);
    }

    // POST — обработка ответа
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quest quest = validator.validateAndGetQuest(req, resp);
        if (quest == null) return;

        req.setAttribute("quest", quest);

        // Обработка выбранного ответа
        Answer selectedAnswer = validator.validateAndGetAnswer(req, resp, quest);
        if (selectedAnswer == null) return;

        long nextQuestionId = selectedAnswer.getNextQuestionId();
        Question nextQuestion = quest.getQuestions().get(nextQuestionId);

        if (nextQuestion != null) {
            req.setAttribute("question", nextQuestion);
            TransferUtil.forward(req, resp, TransferAddress.QUEST_PAGE);
        } else {
            TransferUtil.forward(req, resp, TransferAddress.FINAL_PAGE);
        }
    }
}
