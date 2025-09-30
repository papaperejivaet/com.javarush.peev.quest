package com.quest.controller.servlet;

import com.quest.controller.ScoreManager;
import com.quest.controller.ServletParam;
import com.quest.controller.TransferAddress;
import com.quest.controller.TransferUtil;
import com.quest.controller.request_utils.RequestValidator;
import com.quest.model.Answer;
import com.quest.model.Question;
import com.quest.model.Quest;
import com.quest.util.GeneralConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogicServletTest {

    @Mock
    private RequestValidator validator;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private Quest quest;

    @Mock
    private Question firstQuestion;

    @Mock
    private Question nextQuestion;

    @Mock
    private Answer answer;

    @InjectMocks
    private LogicServlet servlet;

    @BeforeEach
    void setup() {
        lenient().when(req.getSession()).thenReturn(session);

        Map<Long, Question> questions = new HashMap<>();
        questions.put(GeneralConstants.FIRST_QUESTION_ID, firstQuestion);
        questions.put(2L, nextQuestion);

        lenient().when(quest.getQuestions()).thenReturn(questions);
    }

    @Test
    void doGet_questExists_forwardsToQuestPage() throws Exception {
        when(validator.validateAndGetQuest(req, resp)).thenReturn(quest);

        try (MockedStatic<TransferUtil> transferMock = mockStatic(TransferUtil.class);
             MockedStatic<ScoreManager> scoreMock = mockStatic(ScoreManager.class)) {

            servlet.doGet(req, resp);

            verify(req).setAttribute(ServletParam.QUEST, quest);
            verify(req).setAttribute(ServletParam.QUESTION, firstQuestion);
            scoreMock.verify(() -> ScoreManager.resetScore(session));
            transferMock.verify(() -> TransferUtil.forward(req, resp, TransferAddress.QUEST_PAGE));
        }
    }

    @Test
    void doGet_questIsNull_doesNothing() throws Exception {
        when(validator.validateAndGetQuest(req, resp)).thenReturn(null);

        try (MockedStatic<TransferUtil> transferMock = mockStatic(TransferUtil.class);
             MockedStatic<ScoreManager> scoreMock = mockStatic(ScoreManager.class)) {

            servlet.doGet(req, resp);

            verify(req, never()).setAttribute(eq(ServletParam.QUEST), any());
            verify(req, never()).setAttribute(eq(ServletParam.QUESTION), any());
            scoreMock.verifyNoInteractions();
            transferMock.verifyNoInteractions();
        }
    }

    @Test
    void doPost_nextQuestionExists_forwardsToNextQuestion() throws Exception {
        when(validator.validateAndGetQuest(req, resp)).thenReturn(quest);
        when(validator.validateAndGetAnswer(req, resp, quest)).thenReturn(answer);
        when(answer.getNextQuestionId()).thenReturn(2L);

        try (MockedStatic<TransferUtil> transferMock = mockStatic(TransferUtil.class);
             MockedStatic<ScoreManager> scoreMock = mockStatic(ScoreManager.class)) {

            servlet.doPost(req, resp);

            verify(req).setAttribute(ServletParam.QUEST, quest);
            verify(req).setAttribute(ServletParam.QUESTION, nextQuestion);
            scoreMock.verify(() -> ScoreManager.addPoints(session, answer));
            transferMock.verify(() -> TransferUtil.forwardToCorrectPage(req, resp, nextQuestion));
        }
    }

    @Test
    void doPost_nextQuestionIsNull_forwardsToFinalPage() throws Exception {
        when(validator.validateAndGetQuest(req, resp)).thenReturn(quest);
        when(validator.validateAndGetAnswer(req, resp, quest)).thenReturn(answer);
        when(answer.getNextQuestionId()).thenReturn(99L); // нет вопроса с таким ID

        try (MockedStatic<TransferUtil> transferMock = mockStatic(TransferUtil.class);
             MockedStatic<ScoreManager> scoreMock = mockStatic(ScoreManager.class)) {

            servlet.doPost(req, resp);

            verify(req).setAttribute(ServletParam.QUEST, quest);
            scoreMock.verify(() -> ScoreManager.addPoints(session, answer));
            transferMock.verify(() -> TransferUtil.forward(req, resp, TransferAddress.FINAL_PAGE));
        }
    }

    @Test
    void doPost_questIsNull_doesNothing() throws Exception {
        when(validator.validateAndGetQuest(req, resp)).thenReturn(null);

        try (MockedStatic<TransferUtil> transferMock = mockStatic(TransferUtil.class);
             MockedStatic<ScoreManager> scoreMock = mockStatic(ScoreManager.class)) {

            servlet.doPost(req, resp);

            verify(req, never()).setAttribute(any(), any());
            scoreMock.verifyNoInteractions();
            transferMock.verifyNoInteractions();
        }
    }

    @Test
    void doPost_answerIsNull_doesNothing() throws Exception {
        when(validator.validateAndGetQuest(req, resp)).thenReturn(quest);
        when(validator.validateAndGetAnswer(req, resp, quest)).thenReturn(null);

        try (MockedStatic<TransferUtil> transferMock = mockStatic(TransferUtil.class);
             MockedStatic<ScoreManager> scoreMock = mockStatic(ScoreManager.class)) {

            servlet.doPost(req, resp);

            verify(req).setAttribute(ServletParam.QUEST, quest);
            scoreMock.verifyNoInteractions();
            transferMock.verifyNoInteractions();
        }
    }
}
