package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.utils.QuestionPrinter;

public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService questionService = (QuestionService) context.getBean("questionService");
        QuestionPrinter.printQuestion(questionService.getQuestions());
        context.close();
    }
}
