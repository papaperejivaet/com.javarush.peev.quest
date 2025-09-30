package com.quest.controller;

import com.quest.exception.transfer.ForwardException;
import com.quest.exception.transfer.RedirectException;
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
            throw new ForwardException("While trying to forward to \"" + path + "\"");
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
            throw new RedirectException("While trying to redirect to \"" + path + "\"");
        }
    }

    public static void redirectToHome(HttpServletRequest req, HttpServletResponse resp)
    {
        redirect(req, resp, TransferAddress.HOME_PAGE);
    }

    public static void forwardToCorrectPage(HttpServletRequest req, HttpServletResponse resp, Question question)
    {
        if (isFinalQuestion(question))
        {
            req.setAttribute("score", ScoreManager.getScore(req.getSession()));
            TransferUtil.forward(req, resp, TransferAddress.FINAL_PAGE);
        }
        else
        {
            TransferUtil.forward(req, resp, TransferAddress.QUEST_PAGE);
        }
    }

    private static boolean isFinalQuestion(Question question)
    {
        return question.getAnswers() == null || question.getAnswers().isEmpty();
    }
}
