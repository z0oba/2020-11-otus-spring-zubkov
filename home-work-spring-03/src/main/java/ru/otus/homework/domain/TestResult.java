package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TestResult {
    private final Student student;
    private final List<UserAnswer> userAnswers;
    private final boolean isPassed;
    private final int errorLimit;

    public TestResult(Student student, List<UserAnswer> userAnswers, boolean isPassed, int errorLimit) {
        this.student = student;
        this.userAnswers = userAnswers;
        this.isPassed = isPassed;
        this.errorLimit = errorLimit;
    }
}
