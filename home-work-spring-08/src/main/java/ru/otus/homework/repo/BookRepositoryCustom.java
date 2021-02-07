package ru.otus.homework.repo;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

public interface BookRepositoryCustom<Book, Long> {
    List<Book> findBooksByAuthor(Author author);

    List<Book> findBooksByGenre(Genre genre);

    List<Book> findBooksByComment(Comment comment);
}
