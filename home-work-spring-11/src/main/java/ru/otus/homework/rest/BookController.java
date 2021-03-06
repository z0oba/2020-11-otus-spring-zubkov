package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Book> getAllBooks() {
        return
                bookRepository.findAll();
    }

    @PutMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Book> updateBook(@RequestBody Book bookDto, @PathVariable String bookId) {
        return bookRepository.findById(bookId).flatMap(book -> { //need to save comments, when update book
            book.setName(bookDto.getName());
            book.setAuthor(bookDto.getAuthor());
            book.setGenre(bookDto.getGenre());
            return bookRepository.save(book);
        });
    }

    @DeleteMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteBook(@PathVariable String bookId) {
        return bookRepository.findById(bookId)
                .flatMap(book -> commentRepository.deleteAll(book.getComments())) //delete comments of book
                .then(bookRepository.deleteById(bookId)); //then delete book
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/books/{bookId}/comments")
    public Flux<Comment> getAllCommentsForBook(@PathVariable String bookId) {
        return bookRepository.findById(bookId).flatMapIterable(book -> {
            return book.getComments();
        });
    }

    @PostMapping("/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> addCommentForBook(@RequestBody Comment commentDto, @PathVariable String bookId) {
        return bookRepository.findById(bookId)
                .flatMap(book -> {
                    commentDto.setId(new ObjectId().toString());
                    commentDto.setBook(book);
                    return commentRepository.save(commentDto); })
                .flatMap(comment -> {
                    Book book = comment.getBook();
                    if(book.getComments() == null)
                        book.setComments(List.of(comment));
                    else
                        book.getComments().add(comment);
                    return bookRepository.save(book);
                });
    }

    @PutMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Comment> updateComment(@RequestBody Comment commentDto, @PathVariable String commentId) {
        return commentRepository.findById(commentId).flatMap(comment -> { //need to save comments, when update book
            comment.setText(commentDto.getText());
            return commentRepository.save(comment);
        });
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteComment(@PathVariable String commentId) {
        return commentRepository.deleteById(commentId);
    }
}
