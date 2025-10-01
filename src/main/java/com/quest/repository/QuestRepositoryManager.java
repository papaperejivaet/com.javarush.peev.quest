package com.quest.repository;

import com.quest.exception.files.FolderCreationException;
import com.quest.model.Quest;

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
            Quest quest = loader.loadQuest(file);
            repository.addOrUpdateQuest(quest);

        }
    }

    private void initWatcher()
    {
        FileEventHandler handler = new QuestFileEventHandler(repository, loader);
        watcher = new QuestWatcher(folderPath, handler);
        watcher.start();
    }
}
