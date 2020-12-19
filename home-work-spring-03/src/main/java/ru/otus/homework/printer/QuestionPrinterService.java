package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.io.IOService;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.localization.MessageSourceService;

import java.util.List;

@Service
public class QuestionPrinterService implements PrinterService<Question> {

    private static final String DEFAULT_QUESTION_PREFIX = "Question : ";
    private static final String DEFAULT_ANSWERS_PREFIX = "Answers : ";
    private static final String DEFAULT_EMPTY_ANSWER = "Your answer";
    private static final String DEFAULT_PRINT_ALL_QUESTIONS_HEADLINE = "All questions with answers : ";

    private final IOService ioService;
    private final MessageSourceService messageSourceService;

    public QuestionPrinterService(IOService ioService, MessageSourceService messageSourceService) {
        this.ioService = ioService;
        this.messageSourceService = messageSourceService;
    }

    @Override
    public void printAll(List<Question> questions) {
        ioService.printBorder();
        ioService.printItem(messageSourceService.getMessage("all.questions.headline", DEFAULT_PRINT_ALL_QUESTIONS_HEADLINE));
        ioService.printBorder();
        for (Question question : questions) {
            printItem(question);
        }
        ioService.printBorder();
    }

    @Override
    public void printItem(Question question) {
        ioService.printItem(
                messageSourceService.getMessage("question.prefix", DEFAULT_QUESTION_PREFIX) + question.getQuestion()
        );
        ioService.printItem(
                messageSourceService.getMessage("answers.prefix", DEFAULT_ANSWERS_PREFIX)
        );

        if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
            int counter = 0;
            for (String answer : question.getAnswers()) {
                if (answer == null || answer.trim().isEmpty())
                    answer = messageSourceService.getMessage("empty.answer", DEFAULT_EMPTY_ANSWER);

                ioService.printItem(
                        messageSourceService.getMessage("numbered.answer",
                                new String[]{String.valueOf(counter), answer.trim()})
                );
                ++counter;
            }
        } else {
            ioService.printItem(DEFAULT_EMPTY_ANSWER);
        }
    }
}
