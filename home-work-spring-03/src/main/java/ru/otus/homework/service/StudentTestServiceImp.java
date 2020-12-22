package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.domain.UserAnswer;
import ru.otus.homework.localization.MessageSourceService;
import ru.otus.homework.props.AppProps;

import java.util.ArrayList;
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
            CollectNameService collectNameService,
            MessageSourceService messageSource,
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

        //get and check user answers for correct
        List<UserAnswer> userAnswers = collectTestAnswers(questions);

        //check for error limit
        boolean isPassed = !checkErrorLimit(appProps.getErrorLimit(), userAnswers);

        //print test result for student
        IOServiceFacade.printItem(
                new TestResult(new Student(name), userAnswers, isPassed, appProps.getErrorLimit())
        );
    }

    @Override
    public boolean checkErrorLimit(int errorLimit, List<UserAnswer> userAnswers) {
        int counter = 0;
        for (UserAnswer userAnswer : userAnswers) {
            if (!userAnswer.getResult())
                ++counter;
            if (counter > errorLimit)
                return true;
        }
        return false;
    }

    private boolean checkTestAnswer(Question question, String answer) {
        return question.getCorrectAnswer().trim().toLowerCase().equals(
                answer.trim().toLowerCase()
        );
    }

    @Override
    public List<UserAnswer> collectTestAnswers(List<Question> questions) {
        List<UserAnswer> userAnswers = new ArrayList<>();
        for (Question question : questions) {
            IOServiceFacade.printBorder();
            IOServiceFacade.printItem(question);
            String answer = IOServiceFacade.readItem();
            userAnswers.add(new UserAnswer(question, answer, checkTestAnswer(question, answer)));
            IOServiceFacade.printBorder();
        }
        return userAnswers;
    }
}
