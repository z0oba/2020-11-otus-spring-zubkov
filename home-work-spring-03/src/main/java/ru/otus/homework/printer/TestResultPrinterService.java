package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.io.IOServiceImp;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.utils.NumberedStringFormatter;

import java.util.Iterator;
import java.util.List;

@Service
public class TestResultPrinterService implements PrinterService<TestResult> {

    private static final String DEFAULT_ANSWER_AND_RESULT_DELIMITER = " : ";
    private static final String DEFAULT_TEST_FAILED_MESSAGE = "Test failed! Sorry";
    private static final String DEFAULT_TEST_PASSED_MESSAGE = "Test passed! Congratulation!";
    private static final String DEFAULT_ALL_RESULTS_MESSAGE = "All results : ";
    private static final String DEFAULT_RESULTS_MESSAGE = "Results : ";

    private final IOServiceImp ioServiceImp;
    private final NumberedStringFormatter formatter;
    private final MessageSourceImp messageSource;
    private final StudentPrinterService studentPrinterService;

    public TestResultPrinterService(IOServiceImp ioServiceImp, NumberedStringFormatter formatter, MessageSourceImp messageSource, StudentPrinterService studentPrinterService) {
        this.ioServiceImp = ioServiceImp;
        this.formatter = formatter;
        this.messageSource = messageSource;
        this.studentPrinterService = studentPrinterService;
    }

    @Override
    public void printAll(List<TestResult> testResults) {
        ioServiceImp.printBorder();
        ioServiceImp.printItem(messageSource.getMessage("all.results.message", DEFAULT_ALL_RESULTS_MESSAGE));
        ioServiceImp.printBorder();
        for (TestResult testResult : testResults) {
            printItem(testResult);
        }
    }

    @Override
    public void printItem(TestResult testResult) {

        studentPrinterService.printItem(testResult.getStudent());

        ioServiceImp.printBorder();
        ioServiceImp.printItem(messageSource.getMessage("results.message", DEFAULT_RESULTS_MESSAGE));
        ioServiceImp.printBorder();
        Iterator<String> answersIterator = testResult.getAnswers().iterator();
        Iterator<Boolean> resultsIterator = testResult.getResults().iterator();

        int counter = 0;
        while (answersIterator.hasNext() && resultsIterator.hasNext()) {
            String answer = answersIterator.next();
            Boolean result = resultsIterator.next();
            ioServiceImp.printItem(
                    formatter.toNumberedString(counter, String.valueOf(result)) +
                            messageSource.getMessage("answer.and.result.delimiter", DEFAULT_ANSWER_AND_RESULT_DELIMITER) + answer
            );
            ++counter;
        }

        ioServiceImp.printBorder();
        if(testResult.isPassed())
            ioServiceImp.printItem(messageSource.getMessage("test.passed.message", DEFAULT_TEST_PASSED_MESSAGE));
        else
            ioServiceImp.printItem(messageSource.getMessage("test.failed.message", DEFAULT_TEST_FAILED_MESSAGE));

        ioServiceImp.printBorder();
    }
}
