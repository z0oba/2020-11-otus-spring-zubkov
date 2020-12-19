package ru.otus.homework.service;

import java.util.List;

public interface IOServiceFacade {
    void printBorder();

    void printItem(Object item);

    void printAll(List<?> items);

    String readItem();
}
