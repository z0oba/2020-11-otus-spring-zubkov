package ru.otus.homework.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import ru.otus.homework.Application;
import ru.otus.homework.StudentTestRunner;
import ru.otus.homework.configs.AppConfig;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.io.IOServiceImp;
import ru.otus.homework.printer.QuestionPrinterService;
import ru.otus.homework.printer.TestResultPrinterService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service for testing students
 */
@Service
@ComponentScan(basePackages = {"ru.otus.homework.**"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {Application.class, StudentTestRunner.class})})
public class StudentTestServiceImp implements StudentTestService {

    private final static String DEFAULT_STUDENT_TEST_GREETING_MESSAGE = "Welcome for student testing program!";

    private final QuestionPrinterService questionPrinterService;
    private final IOServiceImp ioServiceImp;
    private final QuestionService questionService;
    private final CollectNameServiceImp collectNameServiceImp;
    private final TestResultPrinterService testResultPrinterService;
    private final MessageSourceImp messageSource;
    private final AppConfig appConfig;

    public StudentTestServiceImp(
            QuestionPrinterService questionPrinterService, IOServiceImp ioServiceImp, QuestionService questionService,
            CollectNameServiceImp collectNameServiceImp,
            TestResultPrinterService testResultPrinterService, MessageSourceImp messageSource, AppConfig appConfig) {
        this.questionPrinterService = questionPrinterService;
        this.ioServiceImp = ioServiceImp;
        this.questionService = questionService;
        this.collectNameServiceImp = collectNameServiceImp;
        this.testResultPrinterService = testResultPrinterService;
        this.messageSource = messageSource;
        this.appConfig = appConfig;
    }

    @Override
    public void startTestingSession() {

        //print test greetings
        ioServiceImp.printBorder();
        ioServiceImp.printItem(messageSource.getMessage("student.test.greeting.message", DEFAULT_STUDENT_TEST_GREETING_MESSAGE));

        //get user full name
        String name = collectNameServiceImp.collectName();

        //get questions for test
        List<Question> questions = questionService.getQuestions();

        //get user answers
        List<String> answers = collectTestAnswers(questions);

        //get checked test answers results
        List<Boolean> results = checkTestAnswers(questions, answers);

        //check for error limit
        boolean isPassed = !checkErrorLimit(appConfig.getErrorLimit(), results);

        //print test result for student
        testResultPrinterService.printItem(
                new TestResult(new Student(name), questions, answers, results, isPassed, appConfig.getErrorLimit())
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
            ioServiceImp.printBorder();
            questionPrinterService.printItem(question);
            answers.add(ioServiceImp.readItem());
            ioServiceImp.printBorder();
        }
        return answers;
    }

//    public void run(String... args) {
//        startTestingSession();
//    }
}
