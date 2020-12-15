package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *  Class for Question from *.csv file
 */
@Getter
@Setter
public class Question {
    private final String question;
    private final List<String> answers;
    private final String correctAnswer;

    public Question(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
