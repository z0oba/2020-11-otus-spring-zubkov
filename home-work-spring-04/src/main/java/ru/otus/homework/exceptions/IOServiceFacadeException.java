package ru.otus.homework.exceptions;

public class IOServiceFacadeException extends RuntimeException {
    public IOServiceFacadeException(String message) {
        super(message);
    }

    public IOServiceFacadeException(String message, Throwable cause) {
        super(message, cause);
    }
}
