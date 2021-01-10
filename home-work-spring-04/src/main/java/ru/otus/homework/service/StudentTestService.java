package ru.otus.homework.service;

import ru.otus.homework.domain.TestResult;

public interface StudentTestService {
    void startTestingSession();

    TestResult getLastTestResult();
}
