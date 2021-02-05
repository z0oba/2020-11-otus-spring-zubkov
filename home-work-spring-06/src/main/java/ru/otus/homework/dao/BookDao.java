package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long count();

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    List<Book> findByName(String name);

    void deleteById(long id);
}
