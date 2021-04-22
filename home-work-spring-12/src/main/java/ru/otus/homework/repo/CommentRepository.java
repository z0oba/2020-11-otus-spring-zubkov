package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.book")
    List<Comment> findAll();

    List<Comment> findByBookId(long bookId);
}
