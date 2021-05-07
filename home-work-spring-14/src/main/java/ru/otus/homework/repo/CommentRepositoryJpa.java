package ru.otus.homework.repo;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.dto.CommentJpa;

import java.util.List;

public interface CommentRepositoryJpa extends CrudRepository<CommentJpa, Long> {
//    @Query("select c from comments c join fetch c.book")
//    List<CommentJpa> findAll();

    List<CommentJpa> findByBookId(long bookId);
}
