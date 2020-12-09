package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.QuestionPrinter;

import java.util.List;

public class QuestionServiceImp implements QuestionService {

    private final QuestionDao dao;
    private final QuestionPrinter printer;

    public QuestionServiceImp(QuestionDao dao, QuestionPrinter printer) {
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
    public void printQuestions(){
        printer.printQuestion(dao.findAll()); //print all questions
    }
}
