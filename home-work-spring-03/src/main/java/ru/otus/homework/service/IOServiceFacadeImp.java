package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.exceptions.IOServiceFacadeException;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.printer.QuestionPrinterService;
import ru.otus.homework.printer.StudentPrinterService;
import ru.otus.homework.printer.TestResultPrinterService;

import java.util.List;

@Service
public class IOServiceFacadeImp implements IOServiceFacade {

    private final IOService ioService;
    private final QuestionPrinterService questionPrinterService;
    private final StudentPrinterService studentPrinterService;
    private final TestResultPrinterService testResultPrinterService;

    public IOServiceFacadeImp(IOService ioService, QuestionPrinterService questionPrinterService, StudentPrinterService studentPrinterService, TestResultPrinterService testResultPrinterService) {
        this.ioService = ioService;
        this.questionPrinterService = questionPrinterService;
        this.studentPrinterService = studentPrinterService;
        this.testResultPrinterService = testResultPrinterService;
    }

    @Override
    public void printBorder() {
        ioService.printBorder();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void printItem(Object item) {
        getActualPrinterService(item.getClass()).printItem(item);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void printAll(List<?> items) {
        if (items == null || items.isEmpty())
            throw new IOServiceFacadeException("Empty list error");
        getActualPrinterService(items.get(0).getClass()).printAll(items);
    }

    @Override
    public String readItem() {
        return ioService.readItem();
    }

    //print strings with localization
    @Override
    public void printItem(String message, String defaultValue) {
        ioService.printItem(message, defaultValue);
    }

    @SuppressWarnings("rawtypes")
    private PrinterService getActualPrinterService(Class<?> itemClass) {
        if (itemClass.equals(Student.class))
            return studentPrinterService;
        else if (itemClass.equals(Question.class))
            return questionPrinterService;
        else if (itemClass.equals(TestResult.class))
            return testResultPrinterService;
        else if (itemClass.equals(String.class))
            return ioService;
        else
            throw new IOServiceFacadeException("Unknown class type");
    }
}
