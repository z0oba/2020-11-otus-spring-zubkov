package ru.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionReader;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionDaoException;
import ru.otus.homework.exceptions.QuestionReaderException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DisplayName("Tests question dao imp")
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class QuestionDaoImpTests {

    @MockBean
    private QuestionReader questionReader;

    @MockBean
    private StudentTestRunner studentTestRunner;

    @Autowired
    private QuestionDao questionDao;

    //test question list
    private List<Question> testQuestions;

    @BeforeEach
    void setupTestQuestionList() {
        testQuestions = List.of(
                new Question("How much is the fish?", List.of("10$", "6P"), "6Р"),
                new Question("What is your name?", null, "Joe Doe")
        );
    }

    @Test
    @DisplayName("Getting all questions from reader when they available")
    void findAllAvailableQuestionsTest() throws QuestionReaderException {

        given(questionReader.readQuestions()).willReturn(testQuestions);
        Assertions.assertNotNull(questionDao.findAll());
        Assertions.assertEquals(testQuestions, questionDao.findAll());
    }

    @Test
    @DisplayName("Getting all questions from empty source")
    void findAllQuestionsFromEmptySourceTest() throws QuestionReaderException {

        given(questionReader.readQuestions()).willReturn(new ArrayList<>());
        Assertions.assertNotNull(questionDao.findAll());
        Assertions.assertTrue(questionDao.findAll().isEmpty());
    }


    @Test
    @DisplayName("Getting question from reader by available number")
    void findByNumber() throws QuestionReaderException {
        int number = 0;
        given(questionReader.readQuestions()).willReturn(testQuestions);
        Assertions.assertNotNull(questionDao.findByNumber(number));
        Assertions.assertEquals(testQuestions.get(number), questionDao.findByNumber(number));
    }


    @Test
    @DisplayName("Getting question from reader by negative number")
    void findByNegativeNumber() throws QuestionReaderException {
        int number = -50;
        given(questionReader.readQuestions()).willReturn(testQuestions);
        assertThrows(QuestionDaoException.class, () -> questionDao.findByNumber(number));
    }

    @Test
    @DisplayName("Getting question from reader by unavailable number")
    void findByUnavailableNumber() throws QuestionReaderException {
        int number = 100;
        given(questionReader.readQuestions()).willReturn(testQuestions);
        assertThrows(QuestionDaoException.class, () -> questionDao.findByNumber(number));
    }

    @Test
    @DisplayName("Getting question from empty source by number")
    void findByNumberFromEmptySource() throws QuestionReaderException {
        int number = 100;
        given(questionReader.readQuestions()).willReturn(new ArrayList<>());
        assertThrows(QuestionDaoException.class, () -> questionDao.findByNumber(number));
    }

}
