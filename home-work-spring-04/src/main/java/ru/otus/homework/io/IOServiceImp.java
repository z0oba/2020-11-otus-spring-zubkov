package ru.otus.homework.io;

import org.springframework.stereotype.Service;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.io.reader.ReaderService;
import ru.otus.homework.localization.MessageSourceService;

import java.util.List;

/**
 * Service for working with user IO
 */
@Service
public class IOServiceImp implements IOService {

    private static final String BORDER_MESSAGE = "++++++++++++++++++++++++++++++++++++++++";

    private final PrinterService<String> printerService;
    private final ReaderService<String> readerService;
    private final MessageSourceService messageSourceService;

    public IOServiceImp(PrinterService<String> printerService, ReaderService<String> readerService, MessageSourceService messageSourceService) {
        this.printerService = printerService;
        this.readerService = readerService;
        this.messageSourceService = messageSourceService;
    }

    @Override
    public void printAll(List<String> items) {
        printerService.printAll(items);
    }

    @Override
    public void printItem(String item) {
        printerService.printItem(item);
    }

    @Override
    public String readItem() {
        return readerService.readItem();
    }

    @Override
    public void printBorder() {
        printItem(BORDER_MESSAGE);
    }

    //for print strings with localization
    @Override
    public void printItem(String code, String defaultValue, Object... args) {
        printItem(messageSourceService.getMessage(code, defaultValue, args));
    }

    //for get localized message by it`s code
    @Override
    public String getMessage(String code, String defaultValue) {
        return messageSourceService.getMessage(code, defaultValue);
    }
}
