package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCircuitBreaker
public class Application {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class);
    }
}
