package ru.otus.homework.io.printer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrinterServiceImp implements PrinterService<String> {

    @Override
    public void printAll(List<String> items) {
        for (String item : items) {
            printItem(item);
        }
    }

    @Override
    public void printItem(String item) {
        System.out.println(item);
    }
}
