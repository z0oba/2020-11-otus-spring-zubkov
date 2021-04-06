package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.BookServiceException;
import ru.otus.homework.repo.AuthorRepository;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.GenreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> {
            throw new BookServiceException("Can`t find book by id");
        });
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public Book add(String name, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null)
            author = authorRepository.save(new Author(authorName));

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null)
            genre = genreRepository.save(new Genre(genreName));

        return bookRepository.save(new Book(name, author, genre));
    }

    @Override
    @Transactional
    public long updateById(long id, String name, String authorName, String genreName) {
        if (bookRepository.findById(id).isEmpty())
            throw new BookServiceException("Can`t find book by id for update");
        else {
            Book book = bookRepository.findById(id).get();

            Author author = authorRepository.findByName(authorName);
            if (author == null)
                author = authorRepository.save(new Author(authorName));

            Genre genre = genreRepository.findByName(genreName);
            if (genre == null)
                genre = genreRepository.save(new Genre(genreName));

            book.setName(name);
            book.setAuthor(author);
            book.setGenre(genre);

            return bookRepository.save(book).getId();
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}