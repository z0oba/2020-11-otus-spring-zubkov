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
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionDaoException;
import ru.otus.homework.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@DisplayName("Tests question service imp")
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class QuestionServiceImpTests {
    @MockBean
    private StudentTestRunner studentTestRunner;

    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private QuestionService questionService;

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
    @DisplayName("Getting all questions when they available")
    void getAllQuestionsWhenAvailable() {
        given(questionDao.findAll()).willReturn(testQuestions);
        Assertions.assertNotNull(questionService.getQuestions());
        Assertions.assertEquals(testQuestions, questionService.getQuestions());
    }

    @Test
    @DisplayName("Getting all questions when they not available")
    void getAllQuestionsWhenNotAvailable() {
        given(questionDao.findAll()).willReturn(new ArrayList<>());
        Assertions.assertNotNull(questionService.getQuestions());
        Assertions.assertTrue(questionService.getQuestions().isEmpty()); //must receive an empty list
    }

    @Test
    @DisplayName("Getting an available question by its number")
    void getQuestionByNumberWhenAvailable() {
        int number = 3;
        Question testQuestion = new Question("How much is the fish?", List.of("10$", "6P"), "6P");
        given(questionDao.findByNumber(anyInt())).willReturn(testQuestion);

        Assertions.assertNotNull(questionService.getByNumber(number));
        Assertions.assertEquals(testQuestion, questionService.getByNumber(number));
    }

    @Test
    @DisplayName("Getting not available question by its number")
    void getQuestionByNumberWhenNotAvailable() {
        int number = 100;
        String exceptionMessage = "Can`t find question with number ";
        given(questionDao.findByNumber(number)).willThrow(new QuestionDaoException(exceptionMessage + number));

        Exception exception = assertThrows(QuestionDaoException.class, () -> questionService.getByNumber(number));
        Assertions.assertEquals(exceptionMessage + number, exception.getMessage());
    }


    @Test
    @DisplayName("Getting question by negative number")
    void getQuestionByNegativeNumber() {
        int number = -100;
        String exceptionMessage = "Can`t find question with number ";
        given(questionDao.findByNumber(number)).willThrow(new QuestionDaoException(exceptionMessage + number));

        Exception exception = assertThrows(QuestionDaoException.class, () -> questionService.getByNumber(number));
        Assertions.assertEquals(exceptionMessage + number, exception.getMessage());
    }
}
