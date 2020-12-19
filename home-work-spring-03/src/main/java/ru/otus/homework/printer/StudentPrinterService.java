package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.localization.MessageSourceService;

import java.util.List;

@Service
public class StudentPrinterService implements PrinterService<Student> {

    private static final String DEFAULT_FOR_STUDENT_MESSAGE = "For student : ";

    private final IOService ioService;
    private final MessageSourceService messageSourceService;

    public StudentPrinterService(IOService ioService, MessageSourceService messageSourceService) {
        this.ioService = ioService;
        this.messageSourceService = messageSourceService;
    }

    @Override
    public void printAll(List<Student> students) {
        for (Student student : students) {
            printItem(student);
        }
    }

    @Override
    public void printItem(Student student) {
        ioService.printBorder();
        ioService.printItem(
                messageSourceService.getMessage("for.student.message", DEFAULT_FOR_STUDENT_MESSAGE) + student.getFullName()
        );
    }
}
