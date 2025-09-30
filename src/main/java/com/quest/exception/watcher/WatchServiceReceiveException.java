package com.quest.exception.watcher;

import java.io.IOException;

public class WatchServiceReceiveException extends RuntimeException
{
    public WatchServiceReceiveException(IOException e)
    {
        super(e.getMessage());
    }
}
