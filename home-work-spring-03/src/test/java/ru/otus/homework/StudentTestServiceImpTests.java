package ru.otus.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.homework.io.reader.ReaderServiceImp;
import ru.otus.homework.service.StudentTestServiceImp;

import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
@SpringBootTest
public class StudentTestServiceImpTests {

    @MockBean
    private StudentTestRunner studentTestRunner;

    @MockBean
    private ReaderServiceImp readerService;

    @Autowired
    private StudentTestServiceImp studentTestService;

    @BeforeEach
    void setUpReaderService() {
        when(readerService.readItem()).thenReturn("User test answer");
    }

    @Test
    public void testStudentService() {
        studentTestService.startTestingSession(); //проверяем, что ничего не упало, тест отработал
    }
}
