package ru.otus.homework.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.homework.domain.Book;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {

    private long id = -1;
    private String name;
    private String author;
    private String genre;

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
    }
}
