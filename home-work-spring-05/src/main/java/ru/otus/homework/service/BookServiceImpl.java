package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookDao bookDaoJdbc;

    @Autowired
    private final AuthorDao authorDaoDaoJdbc;

    @Autowired
    private final GenreDao genreDaoJdbc;

    public BookServiceImpl(BookDao bookDaoJdbc, AuthorDao authorDaoDaoJdbc, GenreDao genreDaoJdbc) {
        this.bookDaoJdbc = bookDaoJdbc;
        this.authorDaoDaoJdbc = authorDaoDaoJdbc;
        this.genreDaoJdbc = genreDaoJdbc;
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
        long authorId = authorDaoDaoJdbc.insert(new Author(author));
        long genreId = genreDaoJdbc.insert(new Genre(genre));
        return bookDaoJdbc.insert(new Book(name, new Author(authorId, author), new Genre(genreId, genre)));
    }

    @Override
    public void deleteById(long id) {
        bookDaoJdbc.deleteById(id);
    }
}
