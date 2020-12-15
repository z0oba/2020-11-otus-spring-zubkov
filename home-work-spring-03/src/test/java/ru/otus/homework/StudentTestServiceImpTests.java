package ru.otus.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.configs.AppConfig;
import ru.otus.homework.io.reader.ReaderServiceImp;
import ru.otus.homework.service.StudentTestServiceImp;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties({AppConfig.class})
@SpringBootTest(classes = StudentTestServiceImp.class)
@TestPropertySource(locations = "classpath:application.yaml")
public class StudentTestServiceImpTests {

    @MockBean
    private ReaderServiceImp readerService;

    @Autowired
    private StudentTestServiceImp studentTestService;

    @BeforeEach
    void setUp() {
        when(this.readerService.readItem()).thenReturn("Test user answer");
    }

    @Test
    public void testStudentService() {
        studentTestService.startTestingSession(); //проверяем, что ничего не упало, тест отработал
    }
}
