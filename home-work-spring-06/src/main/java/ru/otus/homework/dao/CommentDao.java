package ru.otus.homework.dao;

import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    long count();

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    List<Comment> findByBookId(long bookId);

    void deleteById(long id);
}
