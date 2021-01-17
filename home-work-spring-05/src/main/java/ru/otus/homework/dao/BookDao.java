package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookDao {
    long count();

    long insert(Book person);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
