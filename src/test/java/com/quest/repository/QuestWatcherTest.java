package com.quest.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestWatcherTest {

    @Mock
    private FileEventHandler eventHandler;

    private QuestWatcher watcher;

    @TempDir
    Path tempDir;

    Path testFile;

    @BeforeEach
    void setUp() {
        watcher = new QuestWatcher(tempDir.toString(), eventHandler);
        watcher.start();

        testFile = tempDir.resolve("file.txt");

        try
        {
            Files.createFile(testFile);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        if (watcher != null) {
            watcher.stop();
        }
    }

    @Test
    void testFileCreateEvent() {

        await().atMost(500, MILLISECONDS)
                        .untilAsserted(() ->
                                verify(eventHandler).onFileCreateOrModify(testFile.toFile()));
    }

    @Test
    void testFileModifyEvent() throws IOException {

        Files.writeString(testFile, "update");

        await().atMost(500, MILLISECONDS)
                .untilAsserted(() -> verify(eventHandler, atLeastOnce()).onFileCreateOrModify(testFile.toFile()));
    }

    @Test
    void testFileDeleteEvent() throws IOException {

        Files.delete(testFile);

        await().atMost(500, MILLISECONDS)
                .untilAsserted(() -> verify(eventHandler).onFileDelete(testFile.getFileName()));
    }

    @Nested
    class StopWatchTests
    {
        @BeforeEach
        void setUp() {
            watcher = new QuestWatcher(tempDir.toString(), eventHandler);
            watcher.start();
        }

        @AfterEach
        void tearDown() {
            if (watcher != null) {
                watcher.stop();
            }
        }

        @Test
        void testStopWatcher()
        {
            Assertions.assertTrue(watcher.isRunning());

            watcher.stop();

            Assertions.assertFalse(watcher.isRunning());
        }
    }

}