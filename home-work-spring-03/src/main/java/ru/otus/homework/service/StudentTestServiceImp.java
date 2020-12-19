package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.localization.MessageSourceService;
import ru.otus.homework.localization.MessageSourceServiceImp;
import ru.otus.homework.printer.TestResultPrinterService;
import ru.otus.homework.props.AppProps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service for testing students
 */
@Service
public class StudentTestServiceImp implements StudentTestService {

    private final static String DEFAULT_STUDENT_TEST_GREETING_MESSAGE = "Welcome for student testing program!";

    private final IOServiceFacade IOServiceFacade; //read and print different objects
    private final QuestionService questionService;
    private final CollectNameService collectNameService;
    private final AppProps appProps;
    private final MessageSourceService messageSource;

    public StudentTestServiceImp(
            IOServiceFacade IOServiceFacade,
            QuestionService questionService,
            CollectNameServiceImp collectNameService,
            TestResultPrinterService testResultPrinterService,
            MessageSourceServiceImp messageSource,
            AppProps appProps) {

        this.IOServiceFacade = IOServiceFacade;
        this.questionService = questionService;
        this.collectNameService = collectNameService;
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public void startTestingSession() {

        //print test greetings
        IOServiceFacade.printBorder();
        IOServiceFacade.printItem(messageSource.getMessage("student.test.greeting.message", DEFAULT_STUDENT_TEST_GREETING_MESSAGE));

        //get user full name
        String name = collectNameService.collectName();

        //get questions for test
        List<Question> questions = questionService.getQuestions();

        //get user answers
        List<String> answers = collectTestAnswers(questions);

        //get checked test answers results
        List<Boolean> results = checkTestAnswers(questions, answers);

        //check for error limit
        boolean isPassed = !checkErrorLimit(appProps.getErrorLimit(), results);

        //print test result for student
        IOServiceFacade.printItem(
                new TestResult(new Student(name), questions, answers, results, isPassed, appProps.getErrorLimit())
        );
    }

    @Override
    public boolean checkErrorLimit(int errorLimit, List<Boolean> results) {
        int counter = 0;
        for (Boolean result : results) {
            if (!result)
                ++counter;
            if (counter > errorLimit)
                return true;
        }
        return false;
    }

    @Override
    public List<Boolean> checkTestAnswers(List<Question> questions, List<String> answers) {
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

    @Override
    public List<String> collectTestAnswers(List<Question> questions) {
        List<String> answers = new ArrayList<>();
        for (Question question : questions) {
            IOServiceFacade.printBorder();
            IOServiceFacade.printItem(question);
            answers.add(IOServiceFacade.readItem());
            IOServiceFacade.printBorder();
        }
        return answers;
    }
}
