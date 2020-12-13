package ru.otus.homework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class NumberedStringFormatter {

    private final String delimiter;

    @Autowired
    public NumberedStringFormatter(@Value("${string.formatter.delimiter}")String delimiter) {
        this.delimiter = delimiter;
    }

    public String toNumberedString(int counter, String string){
        return counter + delimiter + string.trim();
    }
}
