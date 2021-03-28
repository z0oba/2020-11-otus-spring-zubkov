package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.*;
import ru.otus.homework.repo.AuthorRepository;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;
import ru.otus.homework.repo.GenreRepository;

import java.util.List;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private final List<Author> authors = List.of(
            new Author(0L, "Dovlatov"),
            new Author(1L, "Lermontov"),
            new Author(2L, "Bulgakov")
    );

    private final List<Genre> genres = List.of(
            new Genre(0L, "Story"),
            new Genre(1L, "Poems"),
            new Genre(2L, "Novel")
    );

    private final List<Comment> comments = List.of(
            new Comment(0L, "It`s ok!"),
            new Comment(1L, "Not bad"),
            new Comment(2L, "Awesome")
    );

    private final List<Book> books = List.of(
            new Book(0L, "Conservancy area", authors.get(0), genres.get(0), comments.subList(0, 1)),
            new Book(1L, "Mtsyri", authors.get(1), genres.get(1), comments.subList(1, 2)),
            new Book(2L, "Master and Margarita", authors.get(2), genres.get(2), comments.subList(0, 2))
    );

    @ChangeSet(order = "001", id = "dropDb", author = "zubkovia", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initAuthorsRepo", author = "zubkovia", runAlways = true)
    public void initAuthorRepo(AuthorRepository authorRepository) {
        authorRepository.saveAll(authors);
    }

    @ChangeSet(order = "003", id = "initGenresRepo", author = "zubkovia", runAlways = true)
    public void initGenresRepo(GenreRepository genreRepository) {
        genreRepository.saveAll(genres);
    }

    @ChangeSet(order = "004", id = "initCommentRepo", author = "zubkovia", runAlways = true)
    public void initCommentRepo(CommentRepository commentRepository) {
        commentRepository.saveAll(comments);
    }

    @ChangeSet(order = "005", id = "initBookRepo", author = "zubkovia", runAlways = true)
    public void initBookRepo(BookRepository bookRepository) {
        bookRepository.saveAll(books);
    }

    @ChangeSet(order = "006", id = "initSequenceData", author = "zubkovia", runAlways = true)
    public void initBookRepo(MongockTemplate mongoTemplate) {
        mongoTemplate.save(new DatabaseSequence("authors_sequence", authors.size() - 1));
        mongoTemplate.save(new DatabaseSequence("genres_sequence", genres.size() - 1));
        mongoTemplate.save(new DatabaseSequence("books_sequence", books.size() - 1));
        mongoTemplate.save(new DatabaseSequence("comments_sequence", comments.size() - 1));
    }
}
