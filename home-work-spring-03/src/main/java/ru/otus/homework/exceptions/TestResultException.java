package ru.otus.homework.exceptions;

import java.io.IOException;

public class TestResultException extends IOException {
    public TestResultException(String message) {
        super(message);
    }

    public TestResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
