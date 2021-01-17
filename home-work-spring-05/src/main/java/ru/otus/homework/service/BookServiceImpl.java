package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDaoJdbc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookDaoJdbc bookDaoJdbc;

    public BookServiceImpl(BookDaoJdbc bookDaoJdbc) {
        this.bookDaoJdbc = bookDaoJdbc;
    }

    @Override
    public List<Book> getAll() {
        return bookDaoJdbc.getAll();
    }

    @Override
    public Book getById(long id) {
        return bookDaoJdbc.getById(id);
    }

    @Override
    public long count() {
        return bookDaoJdbc.count();
    }

    @Override
    public long insert(String name, String author, String genre) {
        return bookDaoJdbc.insert(new Book(name, new Author(author), new Genre(genre)));
    }

    @Override
    public void deleteById(long id) {
        bookDaoJdbc.deleteById(id);
    }
}
