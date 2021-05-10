package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
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
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "countFallback", commandKey = "count")
    public long count() {
        return commentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "getAllFallback", commandKey = "getAll")
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "getByIdFallback", commandKey = "getById")
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new CommentServiceException("Can`t find comment by id " + id);
        });
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "getByBookIdFallback", commandKey = "getByBookId")
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "addFallback", commandKey = "add")
    public Comment add(long bookId, String text) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return commentRepository.save(new Comment(text, book.get()));
        } else
            throw new CommentServiceException("Can`t find book with id " + bookId);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "deleteByIdFallback", commandKey = "deleteById")
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    @HystrixCommand(groupKey = "commentService", fallbackMethod = "updateByIdFallback", commandKey = "updateById")
    public long updateById(long id, String text) {
        if (commentRepository.findById(id).isEmpty())
            throw new CommentServiceException("Can`t find comment with id " + id);
        else {
            Comment comment = commentRepository.findById(id).get();
            comment.setText(text);
            return commentRepository.save(comment).getId();
        }
    }

    //fallbacks
    public long countFallback() {
        return -1;
    }

    public List<Comment> getAllFallback() {
        return List.of(getEmptyComment());
    }

    public Comment getByIdFallback(long id) {
        Comment comment = getEmptyComment();
        comment.setId(id);
        return comment;
    }

    public List<Comment> getByBookIdFallback(long bookId){
        Comment comment = getEmptyComment();
        comment.getBook().setId(bookId);
        return List.of(comment);
    }

    public Comment addFallback(long bookId, String text) {
        Comment comment = getEmptyComment();
        comment.setText(text);
        comment.setBook(new Book(bookId, "N/A", new Author(0, "N/A"), new Genre(0, "N/A")));
        return comment;
    }

    public void deleteByIdFallback(long id) {
        throw new CommentServiceException("Delete comment timeout for " + id);
    }

    public long updateByIdFallback(long id, String text) {
        return -1;
    }

    private Comment getEmptyComment(){
        return new Comment(
                0,
                "N/A",
                new Book(0, "N/A", new Author(0, "N/A"), new Genre(0, "N/A")));
    }
}
