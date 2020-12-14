package ru.otus.homework.service;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface StudentTestService {
    void startTestingSession();

    boolean checkErrorLimit(int errorLimit, List<Boolean> results);

    List<Boolean> checkTestAnswers(List<Question> questions, List<String> answers);

    List<String> collectTestAnswers(List<Question> questions);
}
