package com.quest.exception;

import java.io.IOException;

public class WatchServiceReceiveException extends RuntimeException
{
    public WatchServiceReceiveException(IOException e)
    {
        super(e.getMessage());
    }
}
