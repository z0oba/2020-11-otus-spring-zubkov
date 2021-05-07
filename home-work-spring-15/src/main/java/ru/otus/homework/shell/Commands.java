package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Student;
import ru.otus.homework.integration.EducationCardForStudentGateway;

@ShellComponent
@RequiredArgsConstructor
public class Commands {

    private final EducationCardForStudentGateway educationCardForStudentGateway;

    @ShellMethod(value = "Start integration flow", key = {"start-integration-flow", "sif"})
    public void startIntegrationFlow(@ShellOption(value = {"--student-name", "-sn"}, help = "name of the book") String studentName) {
        educationCardForStudentGateway.process(new Student(studentName));
    }
}