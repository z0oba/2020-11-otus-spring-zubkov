package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.CommentServiceException;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return commentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new CommentServiceException("Can`t find comment by id " + id);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public Comment add(long bookId, String text) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return commentRepository.save(new Comment(text, book.get()));
        } else
            throw new CommentServiceException("Can`t find book with id " + bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public long updateById(long id, String text) {
        if (commentRepository.findById(id).isEmpty())
            throw new CommentServiceException("Can`t find comment with id " + id);
        else {
            Comment comment = commentRepository.findById(id).get();
            comment.setText(text);
            return commentRepository.save(comment).getId();
        }
    }
}
