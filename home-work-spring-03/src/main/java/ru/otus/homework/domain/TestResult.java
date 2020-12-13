package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TestResult {
    private final List<Question> questions;
    private final List<String> answers;
    private final List<Boolean> results;
    private final boolean isPassed;
    private final int errorLimit;

    public TestResult(List<Question> questions, List<String> answers, List<Boolean> results, boolean isPassed, int errorLimit) {
        this.questions = questions;
        this.answers = answers;
        this.results = results;
        this.isPassed = isPassed;
        this.errorLimit = errorLimit;
    }
}
