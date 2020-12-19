package ru.otus.homework.exceptions;

public class QuestionDaoException extends RuntimeException {
    public QuestionDaoException(String message) {
        super(message);
    }

    public QuestionDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
