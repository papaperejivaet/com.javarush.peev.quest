package com.quest.repository;

import com.quest.exception.FolderCreationException;
import java.io.File;

public class QuestRepositoryManager
{
    private final QuestRepository repository;
    private final QuestFileLoader loader;
    private final String folderPath;
    private QuestWatcher watcher;

    public QuestRepositoryManager(String folderPath)
    {
        this.repository = QuestRepository.getInstance();
        this.loader = new QuestFileLoader();
        this.folderPath = folderPath;
    }

    public void initializeRepository()
    {
        loadExistingQuests();
        initWatcher();
    }

    //На будущее
    public void shutdownRepository()
    {
        if (watcher != null)
        {
            watcher.stop();
        }
    }

    private void loadExistingQuests()
    {
        File folder = new File(folderPath);

        if (!folder.exists())
        {
            boolean isCreated = folder.mkdirs();
            if (!isCreated)
            {
                throw new FolderCreationException("Can't create folder " + folderPath);
            }
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null)
        {
            return;
        }

        for (File file : files)
        {
            Long id = loader.extractId(file);
            if (id != null)
            {
                repository.addOrUpdateQuest(id, loader.loadQuest(file));
            }
        }
    }

    private void initWatcher()
    {
        FileEventHandler handler = new QuestFileEventHandler(repository, loader);
        watcher = new QuestWatcher(folderPath, handler);
        watcher.start();
    }
}
