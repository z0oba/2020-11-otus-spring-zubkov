package ru.otus.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.io.reader.ReaderServiceImp;
import ru.otus.homework.props.AppProps;
import ru.otus.homework.service.StudentTestServiceImp;

import java.util.Locale;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(properties = "test.enabled=true")
public class StudentTestServiceImpTests {

    @MockBean
    private AppProps appProps;

    @MockBean
    private ReaderServiceImp readerService;

    @Autowired
    private StudentTestServiceImp studentTestService;

    @BeforeEach
    void setupAppProps() {
        when(appProps.getLocale()).thenReturn(new Locale("ru_Ru"));
        when(appProps.getErrorLimit()).thenReturn(100);
        when(appProps.getDelimiter()).thenReturn(":");
        when(appProps.getFile()).thenReturn("test-questions.csv");
    }

    @BeforeEach
    void setUpReaderService() {
        when(readerService.readItem()).thenReturn("User test answer");
    }

    @Test
    public void testStudentService() {
        studentTestService.startTestingSession(); //проверяем, что ничего не упало, тест отработал
    }
}
