package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.printer.QuestionPrinterService;
import ru.otus.homework.service.LoginService;
import ru.otus.homework.service.QuestionService;

@ShellComponent
public class QuestionPrinterCommands extends NeedLoggedCommands {

    private final QuestionService questionService;
    private final QuestionPrinterService questionPrinterService;
    private final LoginService loginService;

    @Autowired
    public QuestionPrinterCommands(QuestionService questionService, QuestionPrinterService questionPrinterService, LoginService loginService) {
        super(loginService);
        this.questionService = questionService;
        this.questionPrinterService = questionPrinterService;
        this.loginService = loginService;
    }

    @ShellMethod(value = "Print all test questions with answers variants", key = {"print-all-questions", "paq"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void printAllQuestions() {
        questionPrinterService.printAll(questionService.getQuestions());
    }

    @ShellMethod(value = "Print test question with answer variant by number", key = {"print-question", "pq"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void printQuestion(@ShellOption(value = {"--num", "-n"}, help = "number of requested question") int num) {
        questionPrinterService.printItem(questionService.getByNumber(num));
    }
}
