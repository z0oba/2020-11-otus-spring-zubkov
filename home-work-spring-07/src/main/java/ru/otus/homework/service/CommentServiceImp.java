package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public long count() {
        return commentRepository.count();
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    public long insert(long bookId, String text) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return commentRepository.save(new Comment(text, book.get())).getId();
        } else
            throw new RuntimeException("Can`t find book with id " + bookId);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
