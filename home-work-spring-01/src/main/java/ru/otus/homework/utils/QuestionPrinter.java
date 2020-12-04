package ru.otus.homework.utils;

import ru.otus.homework.domain.Question;

import java.util.List;

public class QuestionPrinter {

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
