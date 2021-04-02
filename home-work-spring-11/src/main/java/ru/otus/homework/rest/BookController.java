package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Book;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.rest.dto.BookDto;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/api/books")
    public Flux<Book> readAll() {
        return bookRepository.findAll();
    }

    @PostMapping("/api/book/update")
    public void updateBook(@RequestBody BookDto bookDto) {
        bookRepository.findById(bookDto.getId());
    }

    @PostMapping("/api/book/delete")
    public void deleteBook(@RequestBody BookDto bookDto) {
        bookRepository.deleteById(bookDto.getId());
    }

//    @PostMapping("/api/book/add")
//    public long addBook(@RequestBody BookDto bookDto) {
//        bookRepository.save(new Book(bookDto.getId(), bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre()));
//    }
}
