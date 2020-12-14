package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.io.IOServiceImp;

@Service
public class CollectNameService {

    private static final String DEFAULT_FULL_NAME_QUESTION_MESSAGE = "Please, enter your full name";
    private final IOServiceImp ioServiceImp;
    private final MessageSourceImp messageSource;

    public CollectNameService(IOServiceImp ioServiceImp, MessageSourceImp messageSource) {
        this.ioServiceImp = ioServiceImp;
        this.messageSource = messageSource;
    }

    public String collectName() {
        ioServiceImp.printBorder();
        ioServiceImp.printItem(messageSource.getMessage("full.name.question.message", DEFAULT_FULL_NAME_QUESTION_MESSAGE));
        String name = ioServiceImp.readItem();
        ioServiceImp.printBorder();
        return name;
    }
}
