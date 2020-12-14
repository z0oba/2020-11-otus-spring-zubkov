package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private final String fullName;

    public Student(String fullName) {
        this.fullName = fullName;
    }
}
