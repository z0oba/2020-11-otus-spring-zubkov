package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class TestingServiceImp implements TestingService {

    private static final String BORDER_MESSAGE = "++++++++++++++++++++++++++++++++++++++++";
    private static final String FULL_NAME_QUESTION_MESSAGE = "Please, enter your full name";
    private static final String TESTING_START_MESSAGE = "Ok! Testing started!";
    private static final String TESTING_RESULT_MESSAGE = "Testing results:";
    private static final String NUMBER_OF_CORRECT_ANSWER_MESSAGE = "Number of correct answers: ";
    private static final String TEST_FAILED_MESSAGE = "Test failed! Sorry";
    private static final String TEST_PASSED_MESSAGE = "Test passed! Congratulation!";

    private final int errorLimit;
    private final QuestionService questionService;

    @Autowired
    public TestingServiceImp(QuestionService questionService, @Value("${error.limit}")int errorLimit) {
        this.questionService = questionService;
        this.errorLimit = errorLimit;
    }

    @Override
    public void startTestingSession() {

        Scanner in = new Scanner(System.in);
        String fullName;
        List<Question> questions = questionService.getQuestions();
        List<Boolean> results = new ArrayList<>();


        //get user fullname
        System.out.println(BORDER_MESSAGE);
        System.out.println(FULL_NAME_QUESTION_MESSAGE);
        fullName = in.nextLine();
        System.out.println(BORDER_MESSAGE);


        //get and chek user answers
        System.out.println(TESTING_START_MESSAGE);
        System.out.println(BORDER_MESSAGE);
        int questionCounter = 0;
        for (Question question : questions) {
            System.out.println(++questionCounter + ")" +question.getQuestion());
            String answer = in.nextLine();
            results.add(
                    answer.trim().toLowerCase().equals(question.getCorrectAnswer().toLowerCase().trim())
            );
        }
        System.out.println(BORDER_MESSAGE);


        //compute a number of correct answers and print results
        System.out.println(TESTING_RESULT_MESSAGE);
        int correctAnswerCounter = 0;
        questionCounter = 0;
        for (Boolean result : results) {
            System.out.println(++questionCounter + ")" + result);
            if (result)
                ++correctAnswerCounter;
        }

        System.out.println(BORDER_MESSAGE);
        System.out.println(NUMBER_OF_CORRECT_ANSWER_MESSAGE + correctAnswerCounter);
        System.out.println(fullName);

        if (correctAnswerCounter >= errorLimit)
            System.out.println(TEST_PASSED_MESSAGE);
        else
            System.out.println(TEST_FAILED_MESSAGE);
        System.out.println(BORDER_MESSAGE);
    }
}
