package ru.otus.homework.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookRepositoryCustomImpl implements BookRepositoryCustom<Book, Long> {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Book> findBooksByAuthor(Author author) {
        return mongoTemplate.find(new Query(Criteria.where("author.$id").is(author.getId())), Book.class, "books");
    }

    @Override
    public List<Book> findBooksByGenre(Genre genre) {
        return mongoTemplate.find(new Query(Criteria.where("genre.$id").is(genre.getId())), Book.class, "books");
    }

    @Override
    public List<Book> findBooksByComment(Comment comment) {
        return mongoTemplate.find(new Query(Criteria.where("comments.$id").is(comment.getId())), Book.class, "books");
    }
}
