package ru.otus.homework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.otus.homework.service.StudentTestService;


/**
 * For staring student tests
 */
@Service
@ConditionalOnProperty(
        value = "test.enabled", havingValue = "false", matchIfMissing = true
)
public class StudentTestRunner implements CommandLineRunner {

    private final StudentTestService studentTestService;

    public StudentTestRunner(StudentTestService studentTestService) {
        this.studentTestService = studentTestService;
    }

    @Override
    public void run(String... args) {
        studentTestService.startTestingSession();
    }
}
