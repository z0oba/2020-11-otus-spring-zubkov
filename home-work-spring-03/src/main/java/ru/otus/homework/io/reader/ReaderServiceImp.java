package ru.otus.homework.io.reader;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ReaderServiceImp implements ReaderService<String> {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readItem() {
        return scanner.nextLine();
    }
}
