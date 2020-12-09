package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionDao {
    Question findByNumber(int number);
    List<Question> findAll();
}
