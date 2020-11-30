package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.util.List;

public class QuestionServiceImp implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImp(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public Question getByNumber(int number) {
        return dao.findByNumber(number);
    }

    @Override
    public List<Question> getQuestions() {
        return dao.findAll();
    }
}
