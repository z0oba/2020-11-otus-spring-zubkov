package ru.otus.homework.localization;

public interface MessageSourceService {
    String getMessage(String code, String defaultMessage, Object... args);
}
