package ru.otus.homework.service;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookService {
    long count();

    List<Book> getAll();

    Book getById(long id);

    Book add(String name, String author, String genre);

    long updateById(long id, String name, String author, String genre);

    void deleteById(long id);
}
