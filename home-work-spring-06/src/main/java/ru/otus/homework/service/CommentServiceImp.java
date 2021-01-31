package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.CommentDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImp implements CommentService {

    @Autowired
    private final CommentDao commentDaoJpa;

    @Autowired
    private final BookDao bookDaoJpa;

    public CommentServiceImp(CommentDao commentDaoJpa, BookDao bookDaoJpa) {
        this.commentDaoJpa = commentDaoJpa;
        this.bookDaoJpa = bookDaoJpa;
    }

    @Override
    public long count() {
        return commentDaoJpa.count();
    }

    @Override
    public List<Comment> getAll() {
        return commentDaoJpa.findAll();
    }

    @Override
    public Comment getById(long id) {
        return commentDaoJpa.findById(id).orElseThrow();
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        return commentDaoJpa.findByBookId(bookId);
    }

    @Override
    public long insert(long bookId, String text) {
        Optional<Book> book = bookDaoJpa.findById(bookId);
        if (book.isPresent()) {
            return commentDaoJpa.save(new Comment(text, book.get())).getId();
        } else
            throw new RuntimeException("Can`t find book with id " + bookId);
    }

    @Override
    public void deleteById(long id) {
        commentDaoJpa.deleteById(id);
    }
}
