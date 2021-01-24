package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorDao {
    long count();

    long insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);
}
