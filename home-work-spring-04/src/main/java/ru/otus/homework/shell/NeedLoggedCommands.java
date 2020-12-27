package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;
import ru.otus.homework.service.LoginService;

@Component
public class NeedLoggedCommands {

    private final LoginService loginService;

    @Autowired
    public NeedLoggedCommands(LoginService loginService) {
        this.loginService = loginService;
    }

    private Availability isLoggedIn() {
        return loginService.isLogged() ? Availability.available() : Availability.unavailable("You need to be logged in before start");
    }
}
