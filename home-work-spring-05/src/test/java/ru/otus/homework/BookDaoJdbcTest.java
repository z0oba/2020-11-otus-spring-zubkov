package ru.otus.homework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.BookDaoJdbc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.Arrays;
import java.util.List;

@DisplayName("Tests of  book dao jdbc")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;
    private static final String TEST_BOOK_NAME = "1984";
    private static final long TEST_BOOK_ID = 4;
    private static final long GET_BOOK_ID = 1;
    private static final long DELETE_BOOK_ID = 2;
    private static final long BOOK_INCORRECT_ID = 100;

    @Autowired
    private BookDao bookDaoJdbc;

    @DisplayName("Get correct book count from db")
    @Test
    void bookCount() {
        Assertions.assertThat(bookDaoJdbc.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Insert new book into DB, author and genre presents at db")
    @Test
    void insertBook() {
        Book book = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, new Author(1, "Dovlatov"), new Genre(3, "Novel"));
        bookDaoJdbc.insert(book);
        Book actualBook = bookDaoJdbc.getById(TEST_BOOK_ID);
        Assertions.assertThat(actualBook).isEqualTo(book);
    }

    @DisplayName("Insert new book into DB, author and genre not presents at db")
    @Test
    void insertBookWithNewAuthorAndGenre() {
        Book book = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, new Author(4, "Orwell"), new Genre(4, "Novel"));
        Assertions.assertThatThrownBy(() -> bookDaoJdbc.insert(book)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("Get book by Id")
    @Test
    void getBookBookById() {
        Book book = new Book(GET_BOOK_ID, "Conservancy area", new Author(1, "Dovlatov"), new Genre(1, "Story"));
        Book actualBook = bookDaoJdbc.getById(GET_BOOK_ID);
        Assertions.assertThat(actualBook).isEqualTo(book);
    }

    @DisplayName("Get all books")
    @Test
    void getAllBooks() {
        Book book1 = new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story"));
        Book book2 = new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems"));
        Book book3 = new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel"));
        List<Book> books = Arrays.asList(book1, book2, book3);
        List<Book> actualBooks = bookDaoJdbc.getAll();
        Assertions.assertThat(actualBooks).isEqualTo(books);
    }

    @DisplayName("Delete book by Id")
    @Test
    void deleteBookBookById() {
        long count = bookDaoJdbc.count();
        bookDaoJdbc.deleteById(DELETE_BOOK_ID);
        Assertions.assertThat(count).isEqualTo(bookDaoJdbc.count() + 1);

        Assertions.assertThatThrownBy(() -> bookDaoJdbc.getById(DELETE_BOOK_ID)).isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessageContaining("Incorrect result size: expected 1, actual 0");
    }

    //get book with incorrect id
    @DisplayName("Get book by incorrect Id")
    @Test
    void getBookByIncorrectId() {
        Assertions.assertThatThrownBy(() -> bookDaoJdbc.getById(BOOK_INCORRECT_ID)).isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessageContaining("Incorrect result size: expected 1, actual 0");
    }

    //delete book with incorrect id
    @DisplayName("Delet book by incorrect Id")
    @Test
    void deleteBookByIncorrectId() {
        bookDaoJdbc.deleteById(BOOK_INCORRECT_ID); //no exception
    }
}
