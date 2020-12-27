package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswer {
    private final Question question;
    private final String answer;
    private final Boolean result;

    public UserAnswer(Question question, String answer, Boolean result) {
        this.question = question;
        this.answer = answer;
        this.result = result;
    }
}
