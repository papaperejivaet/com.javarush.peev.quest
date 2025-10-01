package com.quest.repository;

import com.quest.exception.files.FileMappingException;
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
            throw new FileMappingException(e);
        }
    }


}
