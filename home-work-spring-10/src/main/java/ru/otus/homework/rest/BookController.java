package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repo.BookRepository;
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

//    @PostMapping("/book/edit")
//    public String editBook(@ModelAttribute Book book) {
//        bookService.insert(book);
//        return "redirect:/books";
//    }

    @PostMapping("/api/book/update")
    void updateBook(@RequestBody BookDto bookDto) {
        bookService.insert(
                new Book(bookDto.getId(),
                    bookDto.getName(),
                    new Author(bookDto.getAuthor()),
                    new Genre(bookDto.getGenre())
                )
        );
    }

    @PostMapping("/api/book/delete")
    void deleteBook(@RequestBody BookDto bookDto) {
        bookService.deleteById(bookDto.getId());
    }

    @PostMapping("/api/book/add")
    long addBook(@RequestBody BookDto bookDto) {
        return bookService.insert(bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
    }

}
