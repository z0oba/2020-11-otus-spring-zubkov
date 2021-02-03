package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.CommentDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.CommentServiceException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImp implements CommentService {

    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return commentDao.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentDao.findById(id).orElseThrow(
                () -> {
                    throw new CommentServiceException("Can`t find comment by id");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentDao.findByBookId(bookId);
    }

    @Override
    @Transactional
    public long insert(long bookId, String text) {
        Optional<Book> book = bookDao.findById(bookId);
        if (book.isPresent()) {
            return commentDao.save(new Comment(text, book.get())).getId();
        } else
            throw new CommentServiceException("Can`t find book with id " + bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }
}
