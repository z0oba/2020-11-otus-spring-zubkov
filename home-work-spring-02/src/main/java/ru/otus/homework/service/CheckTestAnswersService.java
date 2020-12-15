package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Service for checking correctness of answers for test questions
 */
@Service
public class CheckTestAnswersService {

    public List<Boolean> getResultList(List<Question> questions, List<String> answers) {
        List<Boolean> results = new ArrayList<>();

        Iterator<Question> questionsIterator = questions.iterator();
        Iterator<String> answersIterator = answers.iterator();

        while (questionsIterator.hasNext() && answersIterator.hasNext()) {
            results.add(
                    questionsIterator.next().getCorrectAnswer().trim().toLowerCase().equals(
                            answersIterator.next().trim().toLowerCase()
                    )
            );
        }
        return results;
    }
}
