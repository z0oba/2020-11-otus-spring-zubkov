package ru.otus.homework.dao;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exceptions.QuestionReaderException;
import ru.otus.homework.props.AppProps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionReader {

    private static final String DEFAULT_FILE_SUFFIX = ".csv";

    private final AppProps appProps;

    public QuestionReader(AppProps appProps) {
        this.appProps = appProps;
    }

    public List<Question> readQuestions() throws QuestionReaderException {

        List<Question> questions = new ArrayList<>();
        ClassLoader classLoader = QuestionReader.class.getClassLoader();

        //get localized or default csv file
        String csvFile = appProps.getLocale() != null ?
                appProps.getFile().replace(DEFAULT_FILE_SUFFIX, "_" + appProps.getLocale() + DEFAULT_FILE_SUFFIX) :
                appProps.getFile();

        try (InputStream inputStream = classLoader.getResourceAsStream(csvFile);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> strings = List.of(line.split(appProps.getDelimiter()));
                if (strings.size() < 3)
                    throw new QuestionReaderException("Incorrect csv line, need more than " + strings.size() + "elements");
                questions.add(
                        new Question(
                                strings.get(0), //first element
                                strings.subList(1, strings.size() - 1),
                                strings.get(strings.size() - 1) //last element is a correct answer
                        )
                );
            }
        } catch (Exception e) {
            throw new QuestionReaderException("Can`t read questions: " + e.getMessage(), e);
        }
        return questions;
    }
}
