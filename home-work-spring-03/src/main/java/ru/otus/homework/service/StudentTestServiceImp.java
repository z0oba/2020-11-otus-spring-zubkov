package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.io.IOService;
import ru.otus.homework.printer.StudentPrinterService;

import java.util.List;

/**
 * Service for testing students
 */
@Service
public class StudentTestServiceImp implements StudentTestService, CommandLineRunner {

    private final static String DEFAULT_STUDENT_TEST_GREETING_MESSAGE = "Welcome for student testing program!";

    private final int errorLimit;
    private final IOService ioService;
    private final QuestionService questionService;
    private final CollectNameService collectNameService;
    private final CollectTestAnswersService collectTestAnswersService;
    private final CheckTestAnswersService checkTestAnswersService;
    private final CheckErrorLimitService checkErrorLimitService;
    private final StudentPrinterService studentPrinterService;
    private final MessageSourceImp messageSource;

    public StudentTestServiceImp(
            @Value("${error.limit}") int errorLimit,
            IOService ioService, QuestionService questionService,
            CollectNameService collectNameService,
            CollectTestAnswersService collectTestAnswersService,
            CheckTestAnswersService checkTestAnswersService,
            CheckErrorLimitService checkErrorLimitService,
            StudentPrinterService studentPrinterService, MessageSourceImp messageSource) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.errorLimit = errorLimit;
        this.collectNameService = collectNameService;
        this.collectTestAnswersService = collectTestAnswersService;
        this.checkTestAnswersService = checkTestAnswersService;
        this.checkErrorLimitService = checkErrorLimitService;
        this.studentPrinterService = studentPrinterService;
        this.messageSource = messageSource;
    }

    @Override
    public void startTestingSession() {

        //print test greetings
        ioService.printBorder();
        ioService.printItem(messageSource.getMessage("student.test.greeting.message", DEFAULT_STUDENT_TEST_GREETING_MESSAGE));

        //get user full name
        String name = collectNameService.collectName();

        //get questions for test
        List<Question> questions = questionService.getQuestions();

        //get user answers
        List<String> answers = collectTestAnswersService.collectAnswers(questions);

        //get checked test answers results
        List<Boolean> results = checkTestAnswersService.getResultList(questions, answers);

        //check for error limit
        boolean isPassed = !checkErrorLimitService.isLimitExceeded(errorLimit, results);

        //test result for student
        Student student = new Student(
                name,
                new TestResult(questions, answers, results, isPassed, errorLimit)
        );

        //print test result for student
        studentPrinterService.printTestResultForStudent(student);
    }

    @Override
    public void run(String... args){
        startTestingSession();
    }
}
