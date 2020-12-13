package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.Question;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.utils.NumberedStringFormatter;

import java.util.List;

@Service
public class QuestionPrinterService implements PrinterService<Question> {

    private static final String DEFAULT_QUESTION_PREFIX = "Question : ";
    private static final String DEFAULT_ANSWERS_PREFIX = "Answers : ";
    private static final String DEFAULT_EMPTY_ANSWER = "Your answer";
    private static final String DEFAULT_PRINT_ALL_QUESTIONS_HEADLINE = "All questions with answers : ";

    private final IOService ioService;
    private final NumberedStringFormatter formatter;
    private final MessageSourceImp messageSource;

    public QuestionPrinterService(IOService ioService, NumberedStringFormatter formatter, MessageSourceImp messageSource) {
        this.ioService = ioService;
        this.formatter = formatter;
        this.messageSource = messageSource;
    }

    @Override
    public void printAll(List<Question> questions) {
        ioService.printBorder();
        ioService.printItem(messageSource.getMessage("all.questions.headline", DEFAULT_PRINT_ALL_QUESTIONS_HEADLINE));
        ioService.printBorder();
        for (Question question : questions) {
            printItem(question);
        }
        ioService.printBorder();
    }

    @Override
    public void printItem(Question question){
        ioService.printItem(
                messageSource.getMessage("question.prefix", DEFAULT_QUESTION_PREFIX) + question.getQuestion()
        );
        ioService.printItem(
                messageSource.getMessage("answers.prefix", DEFAULT_ANSWERS_PREFIX)
        );

        if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
            int counter = 0;
            for (String answer : question.getAnswers()) {
                if(answer ==  null || answer.trim().isEmpty() )
                    answer = messageSource.getMessage("empty.answer", DEFAULT_EMPTY_ANSWER);
                ioService.printItem(formatter.toNumberedString(counter, answer));
                ++counter;
            }
        } else {
            ioService.printItem(DEFAULT_EMPTY_ANSWER);
        }
    }
}
