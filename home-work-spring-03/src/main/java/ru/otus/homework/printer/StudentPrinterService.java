package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;

import java.util.List;

@Service
public class StudentPrinterService implements PrinterService<Student> {

    private static final String DEFAULT_FOR_STUDENT_MESSAGE = "For student : ";

    private final IOService ioService;

    public StudentPrinterService(IOService ioService) {
        this.ioService = ioService;
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
        ioService.printItem("for.student.message", DEFAULT_FOR_STUDENT_MESSAGE, student.getFullName());
    }
}
