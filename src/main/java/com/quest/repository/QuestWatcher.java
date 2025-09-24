package com.quest.repository;

import com.quest.exception.EventsRegisterException;
import com.quest.exception.WatchServiceReceiveException;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuestWatcher
{
    private final Path folderPath;
    private final FileEventHandler eventHandler;
    private ExecutorService executor;
    private WatchService watchService;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public QuestWatcher(String folderPathStr, FileEventHandler eventHandler)
    {
        this.folderPath = Paths.get(folderPathStr);
        this.eventHandler = eventHandler;
    }

    public void start()
    {
        if (isRunning.get())
            return;

        initWatchService();
        initExecutor();

        isRunning.set(true);
        executor.submit(this::watchLoop);
    }

    private void initExecutor()
    {
        executor = Executors.newSingleThreadExecutor(r ->
        {
            Thread t = new Thread(r);
            t.setDaemon(true); // поток демона
            t.setName("QuestWatcherThread");
            return t;
        });
    }

    private void initWatchService()
    {
        try
        {
            watchService = FileSystems.getDefault().newWatchService();
        }
        catch (IOException e)
        {
            throw new WatchServiceReceiveException(e);
        }

        try
        {
            folderPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
        }
        catch (IOException e)
        {
            throw new EventsRegisterException(e);
        }
    }

    public void stop()
    {
        isRunning.set(false);
        closeWatchService();
        shutdownExecutor();
    }

    private void closeWatchService()
    {
        if (watchService != null)
        {
            try
            {
                watchService.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void shutdownExecutor()
    {
        if (executor != null)
        {
            executor.shutdown();
            try
            {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS))
                {
                    executor.shutdownNow();
                }
            }
            catch (InterruptedException e)
            {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void watchLoop()
    {
        try
        {
            while (isRunning.get())
            {
                WatchKey key;
                try
                {
                    key = watchService.take();
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    break;
                }

                for (WatchEvent<?> event : key.pollEvents())
                {
                    processEvent(event);
                }
                key.reset();
            }
        }
        catch (ClosedWatchServiceException ignored)
        {
        }
    }

    private void processEvent(WatchEvent<?> event)
    {
        Path relativePath = (Path) event.context();
        Path fullPath = folderPath.resolve(relativePath);

        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE ||
                event.kind() == StandardWatchEventKinds.ENTRY_MODIFY)
        {
            eventHandler.onFileCreateOrModify(fullPath.toFile());
        }
        else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE)
        {
            eventHandler.onFileDelete(relativePath);
        }
    }
}
