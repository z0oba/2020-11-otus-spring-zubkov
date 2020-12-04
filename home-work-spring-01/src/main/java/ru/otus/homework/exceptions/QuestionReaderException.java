package ru.otus.homework.exceptions;

import java.io.IOException;

public class QuestionReaderException extends IOException {
    public QuestionReaderException(String message) {
        super(message);
    }

    public QuestionReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
