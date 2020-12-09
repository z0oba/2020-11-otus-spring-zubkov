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

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }
}
