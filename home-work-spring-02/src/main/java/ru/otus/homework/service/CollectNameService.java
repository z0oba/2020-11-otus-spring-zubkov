package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.io.IOService;

@Service
public class CollectNameService {

    private static final String FULL_NAME_QUESTION_MESSAGE = "Please, enter your full name";
    private final IOService ioService;

    public CollectNameService(IOService ioService) {
        this.ioService = ioService;
    }

    public String collectName(){
        ioService.printBorder();
        ioService.printItem(FULL_NAME_QUESTION_MESSAGE);
        String name =  ioService.readItem();
        ioService.printBorder();
        return name;
    }
}
