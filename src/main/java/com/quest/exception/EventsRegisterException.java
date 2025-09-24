package com.quest.exception;

import java.io.IOException;

public class EventsRegisterException extends RuntimeException
{
    public EventsRegisterException(IOException e)
    {
        super(e.getMessage());
    }
}
