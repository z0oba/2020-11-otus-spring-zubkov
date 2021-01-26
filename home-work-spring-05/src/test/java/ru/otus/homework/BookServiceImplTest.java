package ru.otus.homework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDaoJdbc;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.BookService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Tests of  book service")
@ActiveProfiles(profiles = "service-test")
@SpringBootTest
public class BookServiceImplTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;
    private static final long TEST_BOOK_ID = 5;
    @MockBean
    private BookDaoJdbc bookDaoJdbc;

    @MockBean
    private AuthorDao authorDaoJdbc;

    @MockBean
    private GenreDao genreDaoJdbc;

    @Autowired
    private BookService bookService;

    @DisplayName("Get book count by bookservice")
    @Test
    void bookCount() {
        given(bookDaoJdbc.count()).willReturn(EXPECTED_BOOKS_COUNT);
        Assertions.assertThat(bookService.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Get book by id with bookservice")
    @Test
    void getBookById() {
        Book expectedBook = new Book(TEST_BOOK_ID, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story"));
        given(bookDaoJdbc.getById(TEST_BOOK_ID)).willReturn(expectedBook);
        Assertions.assertThat(bookService.getById(TEST_BOOK_ID)).isEqualTo(expectedBook);
    }

    @DisplayName("Get all books by id with bookservice")
    @Test
    void getAllBook() {
        Book book1 = new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story"));
        Book book2 = new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems"));
        Book book3 = new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel"));
        List<Book> expectedBooks = Arrays.asList(book1, book2, book3);
        given(bookDaoJdbc.getAll()).willReturn(expectedBooks);
        Assertions.assertThat(bookService.getAll()).isEqualTo(expectedBooks);
    }

    @DisplayName("Insert book by bookservice")
    @Test
    void insertBook() {
        Book book = new Book("1984", new Author("Orwell"), new Genre("Novel"));
        given(bookDaoJdbc.insert(book)).willReturn(TEST_BOOK_ID);
        Assertions.assertThat(bookService.insert("1984", "Orwell", "Novel")).isEqualTo(TEST_BOOK_ID);
    }

    @DisplayName("Delete book by id with bookservice")
    @Test
    void deleteBookById() {
        bookService.deleteById(TEST_BOOK_ID);
        given(bookDaoJdbc.count()).willReturn(EXPECTED_BOOKS_COUNT - 1);
        Assertions.assertThat(bookService.count()).isEqualTo(EXPECTED_BOOKS_COUNT - 1);

        given(bookDaoJdbc.getById(TEST_BOOK_ID)).willThrow(new EmptyResultDataAccessException("Incorrect result size: expected 1, actual 0", 1));
        Assertions.assertThatThrownBy(() -> bookService.getById(TEST_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessageContaining("Incorrect result size: expected 1, actual 0");
    }
}
