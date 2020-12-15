package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.Student;
import ru.otus.homework.io.IOServiceImp;
import ru.otus.homework.io.printer.PrinterService;

import java.util.List;

@Service
public class StudentPrinterService implements PrinterService<Student> {

    private static final String DEFAULT_FOR_STUDENT_MESSAGE = "For student : ";

    private final IOServiceImp ioServiceImp;
    private final MessageSourceImp messageSource;

    public StudentPrinterService(IOServiceImp ioServiceImp, MessageSourceImp messageSource) {
        this.ioServiceImp = ioServiceImp;
        this.messageSource = messageSource;
    }

    @Override
    public void printAll(List<Student> students) {
        for (Student student : students) {
            printItem(student);
        }
    }

    @Override
    public void printItem(Student student) {
        ioServiceImp.printBorder();
        ioServiceImp.printItem(
                messageSource.getMessage("for.student.message", DEFAULT_FOR_STUDENT_MESSAGE) + student.getFullName()
        );
    }
}
