package ru.otus.homework.printer;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.domain.Question;
import ru.otus.homework.io.IOServiceImp;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.utils.NumberedStringFormatter;

import java.util.List;

@Service
public class QuestionPrinterService implements PrinterService<Question> {

    private static final String DEFAULT_QUESTION_PREFIX = "Question : ";
    private static final String DEFAULT_ANSWERS_PREFIX = "Answers : ";
    private static final String DEFAULT_EMPTY_ANSWER = "Your answer";
    private static final String DEFAULT_PRINT_ALL_QUESTIONS_HEADLINE = "All questions with answers : ";

    private final IOServiceImp ioServiceImp;
    private final NumberedStringFormatter formatter;
    private final MessageSourceImp messageSource;

    public QuestionPrinterService(IOServiceImp ioServiceImp, NumberedStringFormatter formatter, MessageSourceImp messageSource) {
        this.ioServiceImp = ioServiceImp;
        this.formatter = formatter;
        this.messageSource = messageSource;
    }

    @Override
    public void printAll(List<Question> questions) {
        ioServiceImp.printBorder();
        ioServiceImp.printItem(messageSource.getMessage("all.questions.headline", DEFAULT_PRINT_ALL_QUESTIONS_HEADLINE));
        ioServiceImp.printBorder();
        for (Question question : questions) {
            printItem(question);
        }
        ioServiceImp.printBorder();
    }

    @Override
    public void printItem(Question question){
        ioServiceImp.printItem(
                messageSource.getMessage("question.prefix", DEFAULT_QUESTION_PREFIX) + question.getQuestion()
        );
        ioServiceImp.printItem(
                messageSource.getMessage("answers.prefix", DEFAULT_ANSWERS_PREFIX)
        );

        if (question.getAnswers() != null && !question.getAnswers().isEmpty()) {
            int counter = 0;
            for (String answer : question.getAnswers()) {
                if(answer ==  null || answer.trim().isEmpty() )
                    answer = messageSource.getMessage("empty.answer", DEFAULT_EMPTY_ANSWER);
                ioServiceImp.printItem(formatter.toNumberedString(counter, answer));
                ++counter;
            }
        } else {
            ioServiceImp.printItem(DEFAULT_EMPTY_ANSWER);
        }
    }
}
