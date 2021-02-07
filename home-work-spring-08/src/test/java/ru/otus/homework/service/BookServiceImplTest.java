package ru.otus.homework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
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
@SpringBootTest
public class BookServiceImplTest {

    private static final List<Author> authors = List.of(
            new Author(0L, "Dovlatov"),
            new Author(1L, "Lermontov"),
            new Author(2L, "Bulgakov")
    );

    private static final List<Genre> genres = List.of(
            new Genre(0L, "Story"),
            new Genre(1L, "Poems"),
            new Genre(2L, "Novel")
    );

    private static final List<Comment> comments = List.of(
            new Comment(0L, "It`s ok!"),
            new Comment(1L, "Not bad"),
            new Comment(2L, "Awesome")
    );

    private static final List<Book> books = List.of(
            new Book(0L, "Conservancy area", authors.get(0), genres.get(0), comments.subList(0, 1)),
            new Book(1L, "Mtsyri", authors.get(1), genres.get(1), comments.subList(1, 2)),
            new Book(2L, "Master and Margarita", authors.get(2), genres.get(2), comments.subList(0, 2))
    );

    private static final long EXPECTED_BOOKS_COUNT = 3;
    private static final long TEST_BOOK_ID = 5;
    private static final Book TEST_BOOK_FROM_DB = books.get(0);
    private static final Book NEW_TEST_BOOK = new Book(TEST_BOOK_ID, "1984", authors.get(0), genres.get(0), comments);
    private static final List<Book> TEST_BOOK_LIST_FROM_DB = books;

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

    @DisplayName("Insert book by bookservice")
    @Test
    void insertBook() {

        given(authorRepository.findByName(NEW_TEST_BOOK.getAuthor().getName()))
                .willReturn(NEW_TEST_BOOK.getAuthor());
        given(genreRepository.findByName(NEW_TEST_BOOK.getGenre().getName()))
                .willReturn(NEW_TEST_BOOK.getGenre());

        given(bookRepository.save(any(Book.class))).willReturn(NEW_TEST_BOOK);

        Assertions.assertThat(bookService.insert(
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
