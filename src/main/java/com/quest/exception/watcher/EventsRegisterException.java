package com.quest.exception.watcher;

import java.io.IOException;

public class EventsRegisterException extends RuntimeException
{
    public EventsRegisterException(IOException e)
    {
        super(e.getMessage());
    }
}
