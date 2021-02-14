package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.rest.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }
}
