package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionDaoException;
import ru.otus.homework.exceptions.QuestionReaderException;
import ru.otus.homework.utils.QuestionReader;

import java.util.List;

public class QuestionDaoImp implements QuestionDao {

    private final QuestionReader questionReader;

    public QuestionDaoImp(QuestionReader questionReader) {
        this.questionReader = questionReader;
    }

    @Override
    public Question findByNumber(int number) {
        try {
            if (questionReader.readQuestions().size() > number && number >= 0)
                return questionReader.readQuestions().get(number);
            else
                throw new QuestionDaoException("Can`t find question with number " + number);
        } catch (QuestionReaderException e) {
            throw new QuestionDaoException(e.getMessage());
        }
    }

    @Override
    public List<Question> findAll() {
        try {
            return questionReader.readQuestions();
        } catch (QuestionReaderException e) {
            throw new QuestionDaoException(e.getMessage(), e.getCause());
        }
    }
}