package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.io.IOService;
import ru.otus.homework.printer.QuestionPrinterService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for collect a answers for test questions
 */

@Service
public class CollectTestAnswersService {

    private final IOService ioService;
    private final QuestionPrinterService questionPrinterService;

    public CollectTestAnswersService(IOService ioService, QuestionPrinterService questionPrinterService) {
        this.ioService = ioService;
        this.questionPrinterService = questionPrinterService;
    }

    public List<String> collectAnswers(List<Question> questions) {
        List<String> answers = new ArrayList<>();
        for (Question question : questions) {
            questionPrinterService.printItem(question);
            answers.add(ioService.readItem());
        }
        return answers;
    }
}
