package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repo.AuthorRepository;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public long insert(String name, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null)
            author = authorRepository.save(new Author(authorName));

        Genre genre = genreRepository.findByName(genreName);
        if (genre == null)
            genre = genreRepository.save(new Genre(genreName));

        Book book = bookRepository.save(new Book(name, author, genre));
        return book.getId();
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}