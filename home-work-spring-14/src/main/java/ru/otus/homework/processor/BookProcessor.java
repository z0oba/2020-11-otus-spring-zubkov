package ru.otus.homework.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.AuthorJpa;
import ru.otus.homework.dto.BookJpa;
import ru.otus.homework.dto.GenreJpa;
import ru.otus.homework.repo.AuthorRepositoryJpa;
import ru.otus.homework.repo.GenreRepositoryJpa;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BookProcessor implements ItemProcessor<Book, BookJpa> {

    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    @Override
    public BookJpa process(Book book) throws Exception {

        BookJpa bookJpa = new BookJpa();
        bookJpa.setName(book.getName());
        bookJpa.setGenre(Objects.requireNonNullElseGet(genreRepositoryJpa.findByName(book.getGenre()),
                () -> new GenreJpa(book.getGenre())));
        bookJpa.setAuthor(Objects.requireNonNullElseGet(authorRepositoryJpa.findByName(book.getAuthor()),
                () -> new AuthorJpa(book.getAuthor())));
        return bookJpa;
    }
}
