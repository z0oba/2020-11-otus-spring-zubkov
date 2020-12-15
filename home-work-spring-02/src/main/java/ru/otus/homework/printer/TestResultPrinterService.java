package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.utils.NumberedStringFormatter;

import java.util.Iterator;
import java.util.List;

@Service
public class TestResultPrinterService implements PrinterService<TestResult> {

    private static final String DEFAULT_ANSWER_AND_RESULT_DELIMITER = " : ";
    private static final String TEST_FAILED_MESSAGE = "Test failed! Sorry";
    private static final String TEST_PASSED_MESSAGE = "Test passed! Congratulation!";
    private static final String ALL_RESULTS_MESSAGE = "All results : ";
    private static final String RESULTS_MESSAGE = "Results : ";

    private final IOService ioService;
    private final NumberedStringFormatter formatter;

    public TestResultPrinterService(IOService ioService, NumberedStringFormatter formatter) {
        this.ioService = ioService;
        this.formatter = formatter;
    }

    @Override
    public void printAll(List<TestResult> testResults) {
        ioService.printBorder();
        ioService.printItem(ALL_RESULTS_MESSAGE);
        ioService.printBorder();
        for(TestResult testResult : testResults){
            printItem(testResult);
        }
    }

    @Override
    public void printItem(TestResult testResult) {
        ioService.printBorder();
        ioService.printItem(RESULTS_MESSAGE);
        ioService.printBorder();
        Iterator<String> answersIterator = testResult.getAnswers().iterator();
        Iterator<Boolean> resultsIterator = testResult.getResults().iterator();

        int counter = 0;
        while (answersIterator.hasNext() && resultsIterator.hasNext()){
            String answer = answersIterator.next();
            Boolean result = resultsIterator.next();
            ioService.printItem(
                    formatter.toNumberedString(counter,answer) + DEFAULT_ANSWER_AND_RESULT_DELIMITER + result
            );
            ++counter;
        }

        ioService.printBorder();
        if(testResult.isPassed())
            ioService.printItem(TEST_PASSED_MESSAGE);
        else
            ioService.printItem(TEST_FAILED_MESSAGE);

        ioService.printBorder();
    }
}
