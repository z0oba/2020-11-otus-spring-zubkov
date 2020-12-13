package ru.otus.homework.io.printer;

import java.util.List;

public interface PrinterService<T> {
    void printAll(List<T> items);
    void printItem(T item);
}
