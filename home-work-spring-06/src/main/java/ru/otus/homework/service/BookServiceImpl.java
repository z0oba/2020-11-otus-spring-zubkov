package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookServiceException;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookDao.findById(id).orElseThrow(
                () -> {
                    throw new BookServiceException("Can`t find book by id");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookDao.count();
    }

    @Override
    @Transactional
    public long insert(String name, String authorName, String genreName) {
        Author author = authorDao.findByName(authorName);
        if (author == null)
            author = authorDao.save(new Author(authorName));

        Genre genre = genreDao.findByName(genreName);
        if (genre == null)
            genre = genreDao.save(new Genre(genreName));

        Book book = bookDao.save(new Book(name, author, genre));
        return book.getId();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}