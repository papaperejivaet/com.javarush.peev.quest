package com.quest.repository;

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

        Long id = loader.extractId(file);
        if (id == null)
            return;
        repository.addOrUpdateQuest(id, loader.loadQuest(file));

    }

    @Override
    public void onFileDelete(Path path)
    {
        File fakeFile = new File(path.getFileName().toString());
        Long id = loader.extractId(fakeFile);
        if (id != null) {
            repository.removeQuest(id);
        }
    }
}
