package com.quest.repository;

import com.quest.exception.QuestNotFoundInCacheException;
import com.quest.model.Quest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    private final Map<Integer, Quest> cache = new ConcurrentHashMap<>();

    //Для вызова в сервлете с кликом на ответ
    public Quest loadQuestById(int id)
    {
        Quest quest = cache.get(id);
        if (quest == null)
        {
            throw new QuestNotFoundInCacheException("Квест с id " + id + " не найден");
        }
        return quest;
    }

    public void addOrUpdateQuest(int id, Quest quest)
    {
        cache.put(id, quest);
    }

    public void removeQuest(int id)
    {
        cache.remove(id);
    }

    public List<String> getQuestNames()
    {
        return cache.values().stream()
                .map(Quest::getName)
                .collect(Collectors.toList());

    }
}
