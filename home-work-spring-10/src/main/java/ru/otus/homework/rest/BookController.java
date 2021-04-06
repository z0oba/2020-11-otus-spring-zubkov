package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        return bookService.getAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody BookDto bookDto, @PathVariable Long bookId) {
        bookService.updateById(
                bookId,
                bookDto.getName(),
                bookDto.getAuthor(),
                bookDto.getGenre()
        );
    }

    @DeleteMapping("/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteById(bookId);
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return BookDto.toDto(bookService.add(
                bookDto.getName(),
                bookDto.getAuthor(),
                bookDto.getGenre()
        ));
    }
}
