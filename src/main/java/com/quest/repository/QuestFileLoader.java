package com.quest.repository;

import com.quest.model.Quest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class QuestFileLoader
{
    private final ObjectMapper mapper = new ObjectMapper();

    public Quest loadQuest(File file)
    {
        try
        {
            return mapper.readValue(file, Quest.class);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Integer extractId(File file)
    {
        String name = file.getName();
        int underscoreIndex = name.lastIndexOf('_');
        int dotIndex = name.lastIndexOf('.');
        if (underscoreIndex != -1 && dotIndex != -1 && underscoreIndex < dotIndex)
        {
            try
            {
                return Integer.parseInt(name.substring(underscoreIndex + 1, dotIndex));
            }
            catch (NumberFormatException ignored)
            {
            }
        }
        return null;
    }
}
