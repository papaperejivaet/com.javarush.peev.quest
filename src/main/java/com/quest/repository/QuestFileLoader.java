package com.quest.repository;

import com.quest.exception.FileMappingException;
import com.quest.model.Quest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class QuestFileLoader
{
    private final ObjectMapper mapper = new ObjectMapper();

    public Quest loadQuest(File file)
    {
        return readFile(file);
    }


    private Quest readFile(File file)
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

    public Long extractId(File file)
    {
        Quest quest = readFile(file);
        return quest.getId();
    }

    //    public Long oldExtractId(File file)
//    {
//        String name = file.getName();
//        int underscoreIndex = name.lastIndexOf('_');
//        int dotIndex = name.lastIndexOf('.');
//        if (underscoreIndex != -1 && dotIndex != -1 && underscoreIndex < dotIndex)
//        {
//            try
//            {
//                return Long.parseLong(name.substring(underscoreIndex + 1, dotIndex));
//            }
//            catch (NumberFormatException ignored)
//            {
//            }
//        }
//        return null;

//    }
}
