package ru.otus.homework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.StudentTestService;


/**
 * For staring student tests
 */
@Component
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
