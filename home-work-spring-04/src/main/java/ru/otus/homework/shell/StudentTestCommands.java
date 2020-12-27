package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework.printer.TestResultPrinterService;
import ru.otus.homework.service.LoginService;
import ru.otus.homework.service.StudentTestService;

@ShellComponent
public class StudentTestCommands extends NeedLoggedCommands {

    private final StudentTestService studentTestService;
    private final TestResultPrinterService testResultPrinterService;
    private final LoginService loginService;

    @Autowired
    public StudentTestCommands(StudentTestService studentTestService, TestResultPrinterService testResultPrinterService, LoginService loginService) {
        super(loginService);
        this.studentTestService = studentTestService;
        this.testResultPrinterService = testResultPrinterService;
        this.loginService = loginService;
    }

    @ShellMethod(value = "Start student testing", key = {"start-test", "st"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void startTest() {
        studentTestService.startTestingSession();
    }

    @ShellMethod(value = "Print last test result", key = {"print-last-test-result", "pltr"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void printLastTestResult() {
        testResultPrinterService.printItem(studentTestService.getLastTestResult());
    }
}
