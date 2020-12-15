package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private final String fullName;
    private final TestResult testResult;

    public Student(String fullName, TestResult testResult) {
        this.fullName = fullName;
        this.testResult = testResult;
    }
}
