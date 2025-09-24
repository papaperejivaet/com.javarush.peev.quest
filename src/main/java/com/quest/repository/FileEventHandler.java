package com.quest.repository;

import java.io.File;
import java.nio.file.Path;

public interface FileEventHandler
{
    void onFileCreateOrModify(File file);

    void onFileDelete(Path path);
}
