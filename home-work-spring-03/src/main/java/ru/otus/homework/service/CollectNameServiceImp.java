package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.io.IOService;

@Service
public class CollectNameServiceImp implements CollectNameService {

    private static final String DEFAULT_FULL_NAME_QUESTION_MESSAGE = "Please, enter your full name";
    private final IOService ioServiceImp;

    public CollectNameServiceImp(IOService ioService) {
        this.ioServiceImp = ioService;
    }

    @Override
    public String collectName() {
        ioServiceImp.printBorder();
        ioServiceImp.printItem("full.name.question.message", DEFAULT_FULL_NAME_QUESTION_MESSAGE);
        String name = ioServiceImp.readItem();
        ioServiceImp.printBorder();
        return name;
    }
}
