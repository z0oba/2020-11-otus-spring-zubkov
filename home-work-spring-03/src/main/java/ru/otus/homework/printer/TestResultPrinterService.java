package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.domain.UserAnswer;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.localization.MessageSourceService;

import java.util.List;

@Service
public class TestResultPrinterService implements PrinterService<TestResult> {

    private static final String DEFAULT_ANSWER_AND_RESULT_DELIMITER = " : ";
    private static final String DEFAULT_TEST_FAILED_MESSAGE = "Test failed! Sorry";
    private static final String DEFAULT_TEST_PASSED_MESSAGE = "Test passed! Congratulation!";
    private static final String DEFAULT_ALL_RESULTS_MESSAGE = "All results : ";
    private static final String DEFAULT_RESULTS_MESSAGE = "Results : ";

    private final IOService ioService;
    private final MessageSourceService messageSourceService;
    private final StudentPrinterService studentPrinterService;

    public TestResultPrinterService(IOService ioService,
                                    MessageSourceService messageSourceService,
                                    StudentPrinterService studentPrinterService) {
        this.ioService = ioService;
        this.messageSourceService = messageSourceService;
        this.studentPrinterService = studentPrinterService;
    }

    @Override
    public void printAll(List<TestResult> testResults) {
        ioService.printBorder();
        ioService.printItem(messageSourceService.getMessage("all.results.message", DEFAULT_ALL_RESULTS_MESSAGE));
        ioService.printBorder();
        for (TestResult testResult : testResults) {
            printItem(testResult);
        }
    }

    @Override
    public void printItem(TestResult testResult) {

        studentPrinterService.printItem(testResult.getStudent());

        ioService.printBorder();
        ioService.printItem(messageSourceService.getMessage("results.message", DEFAULT_RESULTS_MESSAGE));
        ioService.printBorder();


        int counter = 0;
        for (UserAnswer userAnswer : testResult.getUserAnswers()) {
            ioService.printItem(
                    messageSourceService.getMessage("answer.and.result.delimiter",
                            new String[]{
                                    String.valueOf(counter),
                                    userAnswer.getAnswer() + ":" + userAnswer.getResult()},
                            DEFAULT_ANSWER_AND_RESULT_DELIMITER)
            );
            ++counter;
        }

        ioService.printBorder();
        if (testResult.isPassed())
            ioService.printItem(messageSourceService.getMessage("test.passed.message", DEFAULT_TEST_PASSED_MESSAGE));
        else
            ioService.printItem(messageSourceService.getMessage("test.failed.message", DEFAULT_TEST_FAILED_MESSAGE));

        ioService.printBorder();
    }
}
