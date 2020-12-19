package ru.otus.homework.localization;

import org.springframework.lang.Nullable;

public interface MessageSourceService {
    String getMessage(String code);

    String getMessage(String code, String defaultMessage);

    String getFileName(String code);

    String getFileName(String code, String defaultFileName);

    String getMessage(String code, @Nullable Object[] args, String defaultMessage);

    String getMessage(String code, @Nullable Object[] args);
}
