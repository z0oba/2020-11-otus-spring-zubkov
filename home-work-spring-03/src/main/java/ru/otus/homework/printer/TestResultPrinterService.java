package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.domain.UserAnswer;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;

import java.util.List;

@Service
public class TestResultPrinterService implements PrinterService<TestResult> {

    private static final String DEFAULT_ANSWER_AND_RESULT_DELIMITER = " : ";
    private static final String DEFAULT_TEST_FAILED_MESSAGE = "Test failed! Sorry";
    private static final String DEFAULT_TEST_PASSED_MESSAGE = "Test passed! Congratulation!";
    private static final String DEFAULT_ALL_RESULTS_MESSAGE = "All results : ";
    private static final String DEFAULT_RESULTS_MESSAGE = "Results : ";

    private final IOService ioService;
    private final StudentPrinterService studentPrinterService;

    public TestResultPrinterService(IOService ioService,
                                    StudentPrinterService studentPrinterService) {
        this.ioService = ioService;
        this.studentPrinterService = studentPrinterService;
    }

    @Override
    public void printAll(List<TestResult> testResults) {
        ioService.printBorder();
        ioService.printItem("all.results.message", DEFAULT_ALL_RESULTS_MESSAGE);
        ioService.printBorder();
        for (TestResult testResult : testResults) {
            printItem(testResult);
        }
    }

    @Override
    public void printItem(TestResult testResult) {

        studentPrinterService.printItem(testResult.getStudent());

        ioService.printBorder();
        ioService.printItem("results.message", DEFAULT_RESULTS_MESSAGE);
        ioService.printBorder();

        int counter = 0;
        for (UserAnswer userAnswer : testResult.getUserAnswers()) {
            ioService.printItem(
                    "answer.and.result.delimiter",
                    DEFAULT_ANSWER_AND_RESULT_DELIMITER,
                    String.valueOf(counter),
                    userAnswer.getAnswer() + ":" + userAnswer.getResult());
            ++counter;
        }

        ioService.printBorder();
        if (testResult.isPassed())
            ioService.printItem("test.passed.message", DEFAULT_TEST_PASSED_MESSAGE);
        else
            ioService.printItem("test.failed.message", DEFAULT_TEST_FAILED_MESSAGE);

        ioService.printBorder();
    }
}
