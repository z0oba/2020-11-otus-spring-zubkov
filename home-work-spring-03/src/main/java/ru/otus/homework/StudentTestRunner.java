package ru.otus.homework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.homework.configs.AppConfig;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.io.IOServiceImp;
import ru.otus.homework.printer.QuestionPrinterService;
import ru.otus.homework.printer.TestResultPrinterService;
import ru.otus.homework.service.CollectNameServiceImp;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.service.StudentTestServiceImp;


/**
 * For staring student tests
 */
@Service
public class StudentTestRunner extends StudentTestServiceImp implements CommandLineRunner {
    public StudentTestRunner(QuestionPrinterService questionPrinterService, IOServiceImp ioServiceImp, QuestionService questionService, CollectNameServiceImp collectNameServiceImp, TestResultPrinterService testResultPrinterService, MessageSourceImp messageSource, AppConfig appConfig) {
        super(questionPrinterService, ioServiceImp, questionService, collectNameServiceImp, testResultPrinterService, messageSource, appConfig);
    }

    @Override
    public void run(String... args) {
        super.startTestingSession();
    }
}
