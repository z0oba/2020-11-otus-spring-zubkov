package ru.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.service.QuestionServiceImp;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImpTests {

    @Mock
    private QuestionDao questionDao;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImp(questionDao);
    }

    @Test
    void getQuestions() {

        List<Question> testQuestions =  List.of(
                new Question("How much is the fish?", List.of("10$", "6P")),
                new Question("What is your name?", null)
        );

        given(questionDao.findAll()).willReturn(testQuestions);

        Assertions.assertNotNull(questionService.getQuestions());
        Assertions.assertEquals(testQuestions, questionService.getQuestions());
    }

    @Test
    void getQuestionByNumber() {

        Question testQuestion = new Question("How much is the fish?", List.of("10$", "6P"));

        given(questionDao.findByNumber(anyInt())).willReturn(testQuestion);

        Assertions.assertNotNull(questionService.getByNumber(3));
        Assertions.assertEquals(testQuestion, questionService.getByNumber(3));
    }
}
