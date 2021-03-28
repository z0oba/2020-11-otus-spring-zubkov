package ru.otus.homework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repo.AuthorRepository;
import ru.otus.homework.repo.BookRepository;
import ru.otus.homework.repo.CommentRepository;
import ru.otus.homework.repo.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Tests of  book service")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;
    private static final long TEST_BOOK_ID = 5;

    private static final Book TEST_BOOK_FROM_DB = new Book(1L, "Conservancy area",
            new Author(1L, "Dovlatov"), new Genre(1L, "Story"));


    private static final Book NEW_TEST_BOOK = new Book(TEST_BOOK_ID, "1984",
            new Author(4L, "Dovlatov N"), new Genre(4L, "Fantastic Story"));

    private static final List<Book> TEST_BOOK_LIST_FROM_DB = List.of(
            new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story")),
            new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems")),
            new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel"))
    );

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @DisplayName("Get book count by bookservice")
    @Test
    void bookCount() {
        given(bookRepository.count()).willReturn(EXPECTED_BOOKS_COUNT);
        Assertions.assertThat(bookService.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Get book by id with bookservice")
    @Test
    void getBookById() {
        given(bookRepository.findById(TEST_BOOK_ID)).willReturn(Optional.of(TEST_BOOK_FROM_DB));
        Assertions.assertThat(bookService.getById(TEST_BOOK_ID)).isEqualTo(TEST_BOOK_FROM_DB);
    }

    @DisplayName("Get all books by id with bookservice")
    @Test
    void getAllBook() {
        given(bookRepository.findAll()).willReturn(TEST_BOOK_LIST_FROM_DB);
        Assertions.assertThat(bookService.getAll()).isEqualTo(TEST_BOOK_LIST_FROM_DB);
    }

    @DisplayName("Add book by bookservice")
    @Test
    void addBook() {

        given(authorRepository.findByName(NEW_TEST_BOOK.getAuthor().getName()))
                .willReturn(NEW_TEST_BOOK.getAuthor());
        given(genreRepository.findByName(NEW_TEST_BOOK.getGenre().getName()))
                .willReturn(NEW_TEST_BOOK.getGenre());

        given(bookRepository.save(any(Book.class))).willReturn(NEW_TEST_BOOK);

        Assertions.assertThat(bookService.add(
                NEW_TEST_BOOK.getName(),
                NEW_TEST_BOOK.getAuthor().getName(),
                NEW_TEST_BOOK.getGenre().getName()))
                .isEqualTo(NEW_TEST_BOOK.getId());
    }

    @DisplayName("Delete book by id with bookservice")
    @Test
    void deleteBookById() {
        bookService.deleteById(TEST_BOOK_ID);
        given(bookRepository.count()).willReturn(EXPECTED_BOOKS_COUNT - 1);
        Assertions.assertThat(bookService.count()).isEqualTo(EXPECTED_BOOKS_COUNT - 1);
        given(bookRepository.findById(TEST_BOOK_ID)).willReturn(null);
    }
}
