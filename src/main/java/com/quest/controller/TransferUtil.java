package com.quest.controller;

import com.quest.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferUtil
{
    public static void forward(HttpServletRequest req, HttpServletResponse resp, String path)
    {
        try
        {
            req.getRequestDispatcher(path).forward(req, resp);
        }
        catch (ServletException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void redirect(HttpServletRequest req, HttpServletResponse resp, String path)
    {
        try
        {
            resp.sendRedirect(req.getContextPath() + path);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void redirectToHome(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Перенаправляем на стартовую страницу приложения
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isFinalQuestion(Question question) {
        return question.getAnswers() == null || question.getAnswers().isEmpty();
    }

    public static void forwardToCorrectPage(HttpServletRequest req, HttpServletResponse resp, Question question)
            throws ServletException, IOException {
        if (isFinalQuestion(question)) {
            req.setAttribute("score", ScoreManager.getScore(req.getSession()));
            TransferUtil.forward(req, resp, TransferAddress.FINAL_PAGE);
        } else {
            TransferUtil.forward(req, resp, TransferAddress.QUEST_PAGE);
        }
    }
}
