package ru.otus.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

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
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get().getComments();
        } else
            throw new RuntimeException("Can`t find book with id " + bookId);
    }

    @Override
    public long insert(long bookId, String text) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Comment comment = new Comment(sequenceGeneratorService.generateSequence(Comment.SEQUENCE_NAME), text);
            if (book.get().getComments() == null)
                book.get().setComments(new ArrayList<>());
            book.get().getComments().add(comment);
            bookRepository.save(book.get());
            return commentRepository.save(comment).getId();
        } else
            throw new RuntimeException("Can`t find book with id " + bookId);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
