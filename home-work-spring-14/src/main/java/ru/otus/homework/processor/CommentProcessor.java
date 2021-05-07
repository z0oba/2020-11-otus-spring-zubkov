package ru.otus.homework.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.AuthorJpa;
import ru.otus.homework.dto.BookJpa;
import ru.otus.homework.dto.CommentJpa;
import ru.otus.homework.dto.GenreJpa;
import ru.otus.homework.repo.AuthorRepositoryJpa;
import ru.otus.homework.repo.BookRepositoryJpa;
import ru.otus.homework.repo.GenreRepositoryJpa;

@Component
@RequiredArgsConstructor
public class CommentProcessor implements ItemProcessor<Comment, CommentJpa> {

    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    @Override
    public CommentJpa process(Comment comment) throws Exception {

        Book book = comment.getBook();
        GenreJpa genreJpa = genreRepositoryJpa.findByName(book.getGenre());
        AuthorJpa authorJpa = authorRepositoryJpa.findByName(book.getAuthor());
        BookJpa bookJpa = bookRepositoryJpa.findByNameAndGenreAndAuthor(book.getName(), genreJpa, authorJpa);

        CommentJpa commentJpa = new CommentJpa();
        commentJpa.setText(comment.getText());
        commentJpa.setBook(bookJpa);
        return commentJpa;
    }
}
