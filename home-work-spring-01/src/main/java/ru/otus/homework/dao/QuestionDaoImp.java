package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.QuestionReader;

import java.util.List;

public class QuestionDaoImp implements QuestionDao {

    private final String questionSourceFile;

    public QuestionDaoImp(String questionSourceFile) {
        this.questionSourceFile = questionSourceFile;
    }

    @Override
    public Question findByNumber(int number) {
        return QuestionReader.readQuestions(questionSourceFile).get(number);
    }

    @Override
    public List<Question> findAll() {
        return QuestionReader.readQuestions(questionSourceFile);
    }
}