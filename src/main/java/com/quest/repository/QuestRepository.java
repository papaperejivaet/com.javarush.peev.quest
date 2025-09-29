package com.quest.repository;

import com.quest.exception.QuestNotFoundInCacheException;
import com.quest.model.Quest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestRepository
{
    public static volatile QuestRepository instance;

    public static QuestRepository getInstance()
    {
        if (instance == null)
        {
            synchronized (QuestRepository.class)
            {
                if (instance == null)
                {
                    instance = new QuestRepository();
                }
            }
        }
        return instance;
    }

    private final Map<Long, Quest> cache = new ConcurrentHashMap<>();

    public Quest loadQuestById(Long id)
    {
        Quest quest = cache.get(id);
        if (quest == null)
        {
            throw new QuestNotFoundInCacheException("Квест с id " + id + " не найден");
        }
        return quest;
    }

    public void addOrUpdateQuest(Long id, Quest quest)
    {
        cache.put(id, quest);
    }

    public void removeQuest(Long id)
    {
        cache.remove(id);
    }

    public Map<Long, String> getQuests()
    {
        Map<Long, String> namesAndIds = new HashMap<>();
        for (Quest quest : cache.values())
        {
            namesAndIds.put(quest.getId(), quest.getName());
        }
        return namesAndIds;


    }
}
