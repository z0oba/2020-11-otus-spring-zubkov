package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.Student;
import ru.otus.homework.io.IOService;

@Service
public class StudentPrinterService {

    private static final String DEFAULT_FOR_STUDENT_MESSAGE = "For student : ";

    private final IOService ioService;
    private final MessageSourceImp messageSource;
    private final TestResultPrinterService testResultPrinterService;

    public StudentPrinterService(IOService ioService, MessageSourceImp messageSource, TestResultPrinterService testResultPrinterService) {
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.testResultPrinterService = testResultPrinterService;
    }

    public void printTestResultForStudent(Student student){
        ioService.printBorder();
        ioService.printItem(
                messageSource.getMessage("for.student.message", DEFAULT_FOR_STUDENT_MESSAGE) + student.getFullName()
        );
        testResultPrinterService.printItem(student.getTestResult());
    }
}
