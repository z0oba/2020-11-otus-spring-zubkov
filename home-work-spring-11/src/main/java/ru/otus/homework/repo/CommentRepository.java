package ru.otus.homework.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Mono<Comment> findByText(String text);
}