package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookDao bookDaoJpa;

    @Autowired
    private final AuthorDao authorDaoJpa;

    @Autowired
    private final GenreDao genreDaoJpa;

    public BookServiceImpl(BookDao bookDaoJpa, AuthorDao authorDaoJpa, GenreDao genreDaoJpa) {
        this.bookDaoJpa = bookDaoJpa;
        this.authorDaoJpa = authorDaoJpa;
        this.genreDaoJpa = genreDaoJpa;
    }

    @Override
    public List<Book> getAll() {
        return bookDaoJpa.findAll();
    }

    @Override
    public Book getById(long id) {
        return bookDaoJpa.findById(id).orElseThrow();
    }

    @Override
    public long count() {
        return bookDaoJpa.count();
    }

    @Override
    public long insert(String name, String authorName, String genreName) {
        Author author = authorDaoJpa.findByName(authorName);
        if (author == null)
            author = authorDaoJpa.save(new Author(authorName));

        Genre genre = genreDaoJpa.findByName(genreName);
        if (genre == null)
            genre = genreDaoJpa.save(new Genre(genreName));

        Book book = bookDaoJpa.save(new Book(name, author, genre));
        return book.getId();
    }

    @Override
    public void deleteById(long id) {
        bookDaoJpa.deleteById(id);
    }
}