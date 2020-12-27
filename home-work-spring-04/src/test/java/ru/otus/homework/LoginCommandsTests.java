package ru.otus.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for login commands")
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class LoginCommandsTests {

    private static final String DEFAULT_LOGIN_GREETINGS = "Logged as %s";
    private static final String DEFAULT_LOGIN = "testUserName";
    private static final String COMMAND_LOGIN = "login %s";
    private static final String COMMAND_LOGIN_SHORT = "l %s";
    private static final String INCORRECT_LOGIN = "testUserName testValue 123";

    @MockBean
    private StudentTestRunner studentTestRunner;

    @Autowired
    private Shell shell;

    @DisplayName("Test for correct greetings at login by short and long commands")
    @Test
    void testForCorrectGreetingsAtLogin() {
        String res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN, DEFAULT_LOGIN));
        assertThat(res).isEqualTo(String.format(DEFAULT_LOGIN_GREETINGS, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_SHORT, DEFAULT_LOGIN));
        assertThat(res).isEqualTo(String.format(DEFAULT_LOGIN_GREETINGS, DEFAULT_LOGIN));
    }

    @DisplayName("Test for incorrect login params")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterPublishCommandEvaluated() {
        Object res = shell.evaluate(() -> String.format(COMMAND_LOGIN, INCORRECT_LOGIN));
        assertThat(res).isInstanceOf(IllegalArgumentException.class);

        res = shell.evaluate(() -> String.format(COMMAND_LOGIN_SHORT, INCORRECT_LOGIN));
        assertThat(res).isInstanceOf(IllegalArgumentException.class);
    }
}
