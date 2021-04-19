package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;

import java.util.List;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    public static final List<Comment> COMMENTS = List.of(
            new Comment("It`s ok!"),
            new Comment("Not bad"),
            new Comment("Awesome")
    );

    public static final List<Book> BOOKS = List.of(
            new Book("Conservancy area", "Dovlatov", "Story"),
            new Book("Master and Margarita", "Bulgakov", "Novel"),
            new Book("Mtsyri", "Lermontov", "Poems")
    );

    @ChangeSet(order = "001", id = "dropDb", author = "zubkovia", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initDb", author = "zubkovia", runAlways = true)
    public void initDb(MongockTemplate mongoTemplate , BookRepository bookRepository, CommentRepository commentRepository) {
        COMMENTS.forEach(mongoTemplate::save); //save to db comments at first for get a valid id for comments
        BOOKS.forEach(book -> {
            book.setComments(mongoTemplate.findAll(Comment.class));
            mongoTemplate.save(book);
        });
    }
}
