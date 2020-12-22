package ru.otus.homework.io;

import org.springframework.stereotype.Service;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.io.reader.ReaderService;

import java.util.List;

/**
 * Service for working with user IO
 */
@Service
public class IOServiceImp implements IOService {

    private static final String BORDER_MESSAGE = "++++++++++++++++++++++++++++++++++++++++";

    private final PrinterService<String> printerService;
    private final ReaderService<String> readerService;

    public IOServiceImp(PrinterService<String> printerService, ReaderService<String> readerService) {
        this.printerService = printerService;
        this.readerService = readerService;
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
}
