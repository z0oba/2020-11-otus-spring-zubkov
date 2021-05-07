package ru.otus.homework;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableMongock
public class Application {

    public static void main(String[] args) throws Exception {
        Console.main(args);
        ApplicationContext context = SpringApplication.run(Application.class);
    }
}
