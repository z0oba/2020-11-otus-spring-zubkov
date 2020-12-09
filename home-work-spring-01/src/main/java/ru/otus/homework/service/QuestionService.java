package ru.otus.homework.service;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionService {
    //for working with dao
    Question getByNumber(int number);
    List<Question> getQuestions();

    //for print questions
    void printQuestions();
}
