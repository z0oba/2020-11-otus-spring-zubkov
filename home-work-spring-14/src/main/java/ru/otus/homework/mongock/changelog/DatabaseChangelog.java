package ru.otus.homework.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

import java.util.List;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private final List<Comment> comments = List.of(
            new Comment("It`s ok!"),
            new Comment("Not bad"),
            new Comment("Awesome"),
            new Comment("Excelent!")

    );

    private final List<Book> books = List.of(
            new Book("Conservancy area", "Dovlatov", "Story"),
            new Book("Master and Margarita", "Bulgakov", "Novel"),
            new Book("Mtsyri", "Lermontov", "Poems"),
            new Book("Ruslan And Luidmila", "Pushkin", "Poems")

    );

    @ChangeSet(order = "001", id = "initCommentRepo", author = "zubkovia", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initDb", author = "zubkovia", runAlways = true)
    public void initCommentRepo(MongockTemplate mongoTemplate) {
        comments.forEach(mongoTemplate::save); //save to db comments at first for get a valid id for comments
    }

    @ChangeSet(order = "003", id = "initBookRepo", author = "zubkovia", runAlways = true)
    public void initBookRepo(MongockTemplate mongoTemplate) {
        books.forEach(mongoTemplate::save);
    }

    @ChangeSet(order = "004", id = "setCommentsForBooks", author = "zubkovia", runAlways = true)
    public void setCommentsToBookRepo(MongockTemplate mongoTemplate) {
        int counter = 0;
        for (Book book : books) {
            book.setComments(List.of(mongoTemplate.findById(comments.get(counter++).getId(), Comment.class)));
            mongoTemplate.save(book);
        }
    }

    @ChangeSet(order = "05", id = "setBooksForComments", author = "zubkovia", runAlways = true)
    public void setBooksForCommentsRepo(MongockTemplate mongoTemplate) {
        int counter = 0;
        for (Comment comment : comments) {
            comment.setBook(mongoTemplate.findById(books.get(counter++).getId(), Book.class));
            mongoTemplate.save(comment);
        }
    }
}
