package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.configs.MessageSourceImp;
import ru.otus.homework.io.IOService;

@Service
public class CollectNameService {

    private static final String DEFAULT_FULL_NAME_QUESTION_MESSAGE = "Please, enter your full name";
    private final IOService ioService;
    private final MessageSourceImp messageSource;

    public CollectNameService(IOService ioService, MessageSourceImp messageSource) {
        this.ioService = ioService;
        this.messageSource = messageSource;
    }

    public String collectName(){
        ioService.printBorder();
        ioService.printItem(messageSource.getMessage("full.name.question.message", DEFAULT_FULL_NAME_QUESTION_MESSAGE));
        String name =  ioService.readItem();
        ioService.printBorder();
        return name;
    }
}
