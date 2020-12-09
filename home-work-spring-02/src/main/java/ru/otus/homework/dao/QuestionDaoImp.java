package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionDaoException;
import ru.otus.homework.exceptions.QuestionReaderException;

import java.util.List;

@Service
public class QuestionDaoImp implements QuestionDao {

    private final QuestionReader questionReader;

    @Autowired
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
            throw new QuestionDaoException(e.getMessage(), e);
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