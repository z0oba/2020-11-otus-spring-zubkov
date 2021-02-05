package ru.otus.homework.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repo.BookRepository;

import java.util.List;

@DisplayName("Tests of book dao jpa")
@DataJpaTest
public class BookRepositoryTest {

    private static final String NEW_BOOK_NAME = "1984";
    private static final Book TEST_BOOK_FROM_DB = new Book(1L, "Conservancy area",
            new Author(1L, "Dovlatov"), new Genre(1L, "Story"));

    private static final List<Book> TEST_BOOK_LIST_FROM_DB = List.of(
            new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story")),
            new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems")),
            new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel"))
    );

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Get all books")
    @Test
    void findAllBooksTest() {
        Assertions.assertThat(bookRepository.findAll()).isEqualTo(TEST_BOOK_LIST_FROM_DB);
    }

    @DisplayName("Get book by name from repo, bool is exist")
    @Test
    void getBookByNameBookExist() {
        Assertions.assertThat(bookRepository.findByName(TEST_BOOK_FROM_DB.getName()).get(0)).isEqualTo(TEST_BOOK_FROM_DB);
    }

    @DisplayName("Get book by name from repo, bool is not exist")
    @Test
    void getBookByNameBookNotExist() {
        Assertions.assertThat(bookRepository.findByName(NEW_BOOK_NAME).isEmpty()).isTrue();
    }
}
