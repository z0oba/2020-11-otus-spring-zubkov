package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;
import ru.otus.homework.io.IOService;

@Service
public class StudentPrinterService {

    private static final String FOR_STUDENT_MESSAGE = "For student : ";

    private final IOService ioService;
    private final TestResultPrinterService testResultPrinterService;

    public StudentPrinterService(IOService ioService, TestResultPrinterService testResultPrinterService) {
        this.ioService = ioService;
        this.testResultPrinterService = testResultPrinterService;
    }

    public void printTestResultForStudent(Student student){
        ioService.printBorder();
        ioService.printItem(FOR_STUDENT_MESSAGE + student.getFullName());
        testResultPrinterService.printItem(student.getTestResult());
    }
}
