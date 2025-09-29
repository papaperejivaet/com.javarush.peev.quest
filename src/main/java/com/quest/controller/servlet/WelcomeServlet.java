package com.quest.controller.servlet;

import com.quest.controller.TransferAddress;
import com.quest.controller.TransferUtil;
import com.quest.model.Quest;
import com.quest.repository.QuestRepository;
import com.quest.repository.QuestRepositoryManager;
import com.quest.util.JsonPathUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;


@WebServlet(name = "WelcomeServlet", value = "")
public class WelcomeServlet extends HttpServlet
{


    @Override
    public void init()
    {
        String jsonFolderPath = JsonPathUtil.getJsonFolderPath();
        QuestRepositoryManager manager = new QuestRepositoryManager(jsonFolderPath);
        manager.initializeRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Map<Long, String> quests = QuestRepository.getInstance().getQuests();
        req.setAttribute("quests", quests);
        TransferUtil.forward(req, resp, TransferAddress.INDEX_PAGE);
    }
}
