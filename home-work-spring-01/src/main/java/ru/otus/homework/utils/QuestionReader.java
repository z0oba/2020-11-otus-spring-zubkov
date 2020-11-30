package ru.otus.homework.utils;

import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class QuestionReader {

    private static final String DELIMITER = ":";

    public static List<Question> readQuestions(String questionFile) {

        List<Question> questions = new ArrayList<>();
        ClassLoader classLoader = QuestionReader.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(questionFile);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> strings = List.of(line.split(DELIMITER));
                questions.add(
                        new Question(
                                strings.get(0),
                                strings.size() > 1 ? strings.subList(1, strings.size()) : null
                        )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
