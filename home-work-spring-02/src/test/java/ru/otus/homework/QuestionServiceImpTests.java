package ru.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionDaoException;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.service.QuestionServiceImp;
import ru.otus.homework.service.QuestionPrinterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImpTests {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionPrinterService questionPrinter;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImp(questionDao, questionPrinter);
    }

    @Test
    void getQuestions() {

        List<Question> testQuestions =  List.of(
                new Question("How much is the fish?", List.of("10$", "6P"), "6Р"),
                new Question("What is your name?", null, "Joe Doe")
        );

        given(questionDao.findAll()).willReturn(testQuestions);

        Assertions.assertNotNull(questionService.getQuestions());
        Assertions.assertEquals(testQuestions, questionService.getQuestions());
    }

    @Test
    void getQuestionByNumber() {

        Question testQuestion = new Question("How much is the fish?", List.of("10$", "6P"), "6P");

        given(questionDao.findByNumber(anyInt())).willReturn(testQuestion);

        Assertions.assertNotNull(questionService.getByNumber(3));
        Assertions.assertEquals(testQuestion, questionService.getByNumber(3));
    }

    @Test
    void getQuestionByNegativeNumber() {
        int number = -100;
        String exceptionMessage = "Can`t find question with number ";

        given(questionDao.findByNumber(-100)).willThrow(new QuestionDaoException(exceptionMessage + number));

        Exception exception = assertThrows(QuestionDaoException.class, () -> questionService.getByNumber(-100));
        Assertions.assertEquals(exceptionMessage + number, exception.getMessage());
    }
}
