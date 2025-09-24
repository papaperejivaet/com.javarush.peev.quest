package com.quest.controller;

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
}
