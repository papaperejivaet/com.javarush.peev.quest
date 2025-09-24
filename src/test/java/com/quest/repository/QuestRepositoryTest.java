//package com.quest.repository;
//
//import com.quest.model.Quest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class QuestRepositoryTest
//{
//
//    private QuestRepository repository;
//
//    @BeforeEach
//    void setUp()
//    {
//        repository = QuestRepository.getInstance();
//    }
//
//    @Test
//    void testAddOrUpdateQuest()
//    {
//        Quest quest = new Quest(1, "Test Quest");
//        repository.addOrUpdateQuest(1, quest);
//
//        Quest fetched = repository.getQuest(1);
//        assertNotNull(fetched);
//        assertEquals("Test Quest", fetched.getName());
//    }
//
//    @Test
//    void testRemoveQuest()
//    {
//        Quest quest = new Quest(1, "Test Quest");
//        repository.addOrUpdateQuest(1, quest);
//        repository.removeQuest(1);
//
//        assertNull(repository.loadQuestById(1));
//    }
//}
//
