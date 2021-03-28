package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/book/update")
    public void updateBook(@RequestBody BookDto bookDto) {
        bookService.updateById(
                    bookDto.getId(),
                    bookDto.getName(),
                    bookDto.getAuthor(),
                    bookDto.getGenre()
        );
    }

    @PostMapping("/api/book/delete")
    public void deleteBook(@RequestBody BookDto bookDto) {
        bookService.deleteById(bookDto.getId());
    }

    @PostMapping("/api/book/add")
    public long addBook(@RequestBody BookDto bookDto) {
        return bookService.add(
                bookDto.getName(),
                bookDto.getAuthor(),
                bookDto.getGenre()
        );
    }
}
