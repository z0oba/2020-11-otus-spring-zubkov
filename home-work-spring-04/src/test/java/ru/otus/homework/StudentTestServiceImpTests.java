package ru.otus.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.homework.io.reader.ReaderServiceImp;
import ru.otus.homework.service.StudentTestServiceImp;

import static org.mockito.Mockito.when;

@DisplayName("Tests student service imp")
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class StudentTestServiceImpTests {

    @MockBean
    private StudentTestRunner studentTestRunner;

    @MockBean
    private ReaderServiceImp readerService;

    @Autowired
    private StudentTestServiceImp studentTestService;

    @Test
    @DisplayName("Checking for the full cycle of student testing with constant user answer")
    public void testStudentService() {
        when(readerService.readItem()).thenReturn("User test answer");
        studentTestService.startTestingSession();
    }

    @Test
    @DisplayName("Full student test with all correct answers")
    public void testStudentServiceWithCorrectAnswers() {
        when(readerService.readItem())
                .thenReturn("Joe Doe")
                .thenReturn("8")
                .thenReturn("0")
                .thenReturn("Энцелад")
                .thenReturn("300000")
                .thenReturn("марс");
        studentTestService.startTestingSession();
        Assertions.assertTrue(studentTestService.getLastTestResult().isPassed());
    }

    @Test
    @DisplayName("Full student test with all incorrect answers")
    public void testStudentServiceWithIncorrectCorrectAnswers() {
        when(readerService.readItem())
                .thenReturn("Joe Doe")
                .thenReturn("0")
                .thenReturn("2")
                .thenReturn("Unknown")
                .thenReturn("150000")
                .thenReturn("planet");
        studentTestService.startTestingSession();
        Assertions.assertFalse(studentTestService.getLastTestResult().isPassed());
    }
}
