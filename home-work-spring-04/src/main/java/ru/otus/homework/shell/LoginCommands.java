package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.service.LoginService;

@ShellComponent
public class LoginCommands {
    public static String DEFAULT_LOGIN_GREETINGS = "Logged as ";

    private final LoginService loginService;

    public LoginCommands(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = "Login to application", key = {"login", "l"})
    public String login(@ShellOption String userName) {
        loginService.setUserName(userName);
        return DEFAULT_LOGIN_GREETINGS + loginService.getUserName();
    }
}
