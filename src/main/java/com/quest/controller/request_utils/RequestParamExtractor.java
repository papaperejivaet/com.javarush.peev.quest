package com.quest.controller.request_utils;

import com.quest.controller.ServletParam;
import com.quest.controller.TransferUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class RequestParamExtractor
{
    public String getParam(HttpServletRequest req, HttpServletResponse resp, String paramName) {
        String param = req.getParameter(paramName);
        if (param == null) {
            TransferUtil.redirectToHome(req, resp);
            return null;
        }
        return param;
    }


}
