package com.quest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonPathUtil
{
    public static String getJsonFolderPath() {
        String folderPath = GeneralConstants.QUEST_JSON_FOLDER;

        if (folderPath == null || folderPath.isEmpty()) {
            String userHome = System.getProperty("user.home");
            folderPath = userHome + File.separator + "com.javarush.peev.quest" + File.separator + "json";
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folderPath;
        }
}
