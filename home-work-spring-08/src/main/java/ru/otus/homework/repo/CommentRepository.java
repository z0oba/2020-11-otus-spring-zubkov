package ru.otus.homework.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,Long> {
    List<Comment> findByBookId(long bookId);
}
