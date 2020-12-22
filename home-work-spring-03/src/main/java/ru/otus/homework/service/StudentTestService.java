package ru.otus.homework.service;

import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.UserAnswer;

import java.util.List;

public interface StudentTestService {
    void startTestingSession();

    boolean checkErrorLimit(int errorLimit, List<UserAnswer> userAnswers);

    List<UserAnswer> collectTestAnswers(List<Question> questions);
}
