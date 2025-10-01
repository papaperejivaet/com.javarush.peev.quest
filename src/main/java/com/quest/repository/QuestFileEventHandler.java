package com.quest.repository;

import com.quest.model.Quest;

import java.io.File;
import java.nio.file.Path;

public class QuestFileEventHandler implements FileEventHandler
{
    private final QuestRepository repository;
    private final QuestFileLoader loader;

    public QuestFileEventHandler(QuestRepository repository, QuestFileLoader loader)
    {
        this.repository = repository;
        this.loader = loader;
    }

    @Override
    public void onFileCreateOrModify(File file)
    {
        if (!file.exists() || !file.getName().endsWith(".json"))
            return;

        Quest quest = loader.loadQuest(file);
        repository.addOrUpdateQuest(quest);

    }

    @Override
    public void onFileDelete(Path path)
    {
        File fakeFile = new File(path.getFileName().toString());
        Long id = loader.loadQuest(fakeFile).getId();
        if (id != null) {
            repository.removeQuest(id);
        }
    }
}
