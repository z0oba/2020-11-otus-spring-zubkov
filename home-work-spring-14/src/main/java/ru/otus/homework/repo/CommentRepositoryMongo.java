package ru.otus.homework.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Comment;

public interface CommentRepositoryMongo extends MongoRepository<Comment, String> {
    Comment findByText(String text);
}