package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.service.StudentTestService;

@PropertySource("application.properties")
@Configuration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        //start testing
        StudentTestService studentTestService = context.getBean(StudentTestService.class);
        studentTestService.startTestingSession();
        context.close();
    }
}
