package ru.otus.homework.io;

import org.springframework.stereotype.Service;
import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.io.printer.PrinterServiceImp;
import ru.otus.homework.io.reader.ReaderService;
import ru.otus.homework.io.reader.ReaderServiceImp;

import java.util.List;

/**
 *  Service for working with user IO
 */
@Service
public class IOService implements PrinterService<String>, ReaderService<String> {

    private static final String BORDER_MESSAGE = "++++++++++++++++++++++++++++++++++++++++";

    private final PrinterServiceImp printerService;
    private final ReaderServiceImp readerService;

    public IOService(PrinterServiceImp printerService, ReaderServiceImp readerServiceImp) {
        this.printerService = printerService;
        this.readerService = readerServiceImp;
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

    public void printBorder(){
        printItem(BORDER_MESSAGE);
    }
}
