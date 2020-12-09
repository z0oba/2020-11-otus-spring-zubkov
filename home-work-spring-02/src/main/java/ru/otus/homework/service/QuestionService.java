package ru.otus.homework.service;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionService {
    Question getByNumber(int number);
    List<Question> getQuestions();

    void printQuestions();
}
