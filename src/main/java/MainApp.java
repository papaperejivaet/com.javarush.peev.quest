import com.quest.controller.ServletParam;
import com.quest.repository.QuestRepository;
import com.quest.repository.QuestRepositoryManager;
import com.quest.util.GeneralConstants;

import java.util.Map;

//Тестовый класс
public class MainApp
{
    public static void main(String[] args)
    {
        QuestRepositoryManager manager
                = new QuestRepositoryManager(GeneralConstants.QUEST_JSON_FOLDER);

        manager.initializeRepository();

        System.out.println(ServletParam.CURRENT_QUESTION_ID);

        QuestRepository repo = QuestRepository.getInstance();
        while (true)
        {
            Map<Long, String> quests = repo.getQuests();
            for (Map.Entry<Long, String> entry : quests.entrySet())
            {
                System.out.println(repo.loadQuestById(entry.getKey()).getName());

            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }



    }
}
