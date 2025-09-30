package com.quest.controller;

import com.quest.model.Answer;
import com.quest.util.GeneralConstants;
import jakarta.servlet.http.HttpSession;

public class ScoreManager
{



    public static void addPoints(HttpSession session, Answer answer) {
        if (answer != null && answer.isCorrect()) {
            int currentScore = getScore(session);
            session.setAttribute(ServletParam.SCORE.toString(), currentScore + GeneralConstants.POINTS_PER_CORRECT_ANSWER);
        }
    }

    public static int getScore(HttpSession session) {
        Object scoreObj = session.getAttribute(ServletParam.SCORE.toString());
        if (scoreObj instanceof Integer) {
            return (Integer) scoreObj;
        }
        return 0;
    }


    public static void resetScore(HttpSession session) {
        session.setAttribute(ServletParam.SCORE.toString(), 0);
    }
}
