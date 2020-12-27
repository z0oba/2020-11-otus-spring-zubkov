package ru.otus.homework.io;

import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.io.reader.ReaderService;

public interface IOService extends PrinterService<String>, ReaderService<String> {
    void printBorder();

    //for work with localized strings
    void printItem(String code, String defaultValue, Object... values);

    String getMessage(String code, String defaultValue);
}
