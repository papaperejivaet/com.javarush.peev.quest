package com.quest.exception;

public class QuestNotFoundInCacheException extends RuntimeException
{
    public QuestNotFoundInCacheException(String message)
    {
        super(message);
    }
}
