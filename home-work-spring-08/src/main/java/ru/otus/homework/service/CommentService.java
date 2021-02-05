package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentService {
    long count();

    List<Comment> getAll();

    Comment getById(long id);

    List<Comment> getByBookId(long bookId);

    long insert(long bookId, String text);

    void deleteById(long id);
}
