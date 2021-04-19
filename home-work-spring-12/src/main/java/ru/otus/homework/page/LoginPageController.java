package ru.otus.homework.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginPageController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "/login";
    }
}
