package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService {

    private final QuestionDao dao;
    private final QuestionPrinterService printer;

    @Autowired
    public QuestionServiceImp(QuestionDao dao, QuestionPrinterService printer) {
        this.dao = dao;
        this.printer = printer;
    }

    @Override
    public Question getByNumber(int number) {
        return dao.findByNumber(number);
    }

    @Override
    public List<Question> getQuestions() {
        return dao.findAll();
    }

    @Override
    public void printQuestions() {
        printer.printQuestion(dao.findAll()); //print all questions
    }
}
