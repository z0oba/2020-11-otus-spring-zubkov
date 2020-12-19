package ru.otus.homework.io;

import ru.otus.homework.io.printer.PrinterService;
import ru.otus.homework.io.reader.ReaderService;

public interface IOService extends PrinterService<String>, ReaderService<String> {
    void printBorder();
}
