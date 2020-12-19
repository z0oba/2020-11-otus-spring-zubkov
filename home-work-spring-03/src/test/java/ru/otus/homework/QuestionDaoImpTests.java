package ru.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionReader;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionDaoException;
import ru.otus.homework.exceptions.QuestionReaderException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = "test.enabled=true")
public class QuestionDaoImpTests {

    @MockBean
    private QuestionReader questionReader;

    @Autowired
    private QuestionDao questionDao;

    @Test
    void findAllTest() throws QuestionReaderException {

        List<Question> testQuestions = List.of(
                new Question("How much is the fish?", List.of("10$", "6P"), "6Р"),
                new Question("What is your name?", null, "Joe Doe")
        );

        given(questionReader.readQuestions()).willReturn(testQuestions);

        Assertions.assertNotNull(questionDao.findAll());
        Assertions.assertEquals(testQuestions, questionDao.findAll());
    }

    @Test
    void findByNegativeNumber() throws QuestionReaderException {
        int number = -50;

        List<Question> testQuestions = List.of(
                new Question("How much is the fish?", List.of("10$", "6P"), "6Р"),
                new Question("What is your name?", null, "Joe Doe")
        );

        given(questionReader.readQuestions()).willReturn(testQuestions);

        assertThrows(QuestionDaoException.class, () -> questionDao.findByNumber(number));
    }

    @Test
    void findByNumberFromEmptySource() throws QuestionReaderException {
        int number = -50;

        List<Question> testQuestions = List.of(
                new Question("How much is the fish?", List.of("10$", "6P"), "6Р"),
                new Question("What is your name?", null, "Joe Doe")
        );

        given(questionReader.readQuestions()).willReturn(testQuestions);

        assertThrows(QuestionDaoException.class, () -> questionDao.findByNumber(number));
    }
}
