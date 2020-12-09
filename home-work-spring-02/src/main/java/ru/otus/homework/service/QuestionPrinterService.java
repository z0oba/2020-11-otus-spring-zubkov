package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class QuestionPrinterService {

    private static final String DEFAULT_QUESTION_PREFIX = "Question :";
    private static final String DEFAULT_ANSWERS_PREFIX = "Answers :";
    private static final String DEFAULT_EMPTY_ANSWER = "your answer";

    public void printQuestion(List<Question> questions) {
        for (Question question : questions) {
            System.out.println(DEFAULT_QUESTION_PREFIX + question.getQuestion());
            System.out.println(DEFAULT_ANSWERS_PREFIX);
            if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
                int counter = 0;
                for (String answer : question.getAnswers()) {
                    System.out.println(counter + ")" + answer);
                    ++counter;
                }
            } else {
                System.out.println(DEFAULT_EMPTY_ANSWER);
            }
        }
    }
}
