package ru.otus.homework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.BookDaoJpa;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

@DisplayName("Tests of book dao jpa")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(BookDaoJpa.class)
public class BookDaoJpaTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;
    private static final long TEST_BOOK_ID = 4;
    private static final long DELETE_BOOK_ID = 2;
    private static final long BOOK_INCORRECT_ID = 100;

    private static final Book TEST_BOOK_FROM_DB = new Book(1L, "Conservancy area",
            new Author(1L, "Dovlatov"), new Genre(1L, "Story"));

    private static final Book NEW_TEST_BOOK = new Book(TEST_BOOK_ID, "1984",
            new Author(1L, "Dovlatov"), new Genre(1L, "Story"));

    private static final Book NEW_TEST_BOOK_WITH_UNKNOWN_AUTHOR_GENRE = new Book(TEST_BOOK_ID, "1984",
            new Author(4L, "Dovlatov N"), new Genre(1L, "Fantastic Story"));

    private static final List<Book> TEST_BOOK_LIST_FROM_DB = List.of(
            new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story")),
            new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems")),
            new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel"))
    );

    @Autowired
    private BookDao bookDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Get correct book count from db")
    @Test
    void bookCount() {
        Assertions.assertThat(bookDaoJpa.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Insert new book into DB, author and genre presents at db")
    @Test
    void insertBook() {
        bookDaoJpa.save(NEW_TEST_BOOK);
        Book actualBook = bookDaoJpa.findById(NEW_TEST_BOOK.getId()).orElseThrow();
        Book emBook = em.find(Book.class, TEST_BOOK_ID);

        Assertions.assertThat(actualBook).isEqualTo(NEW_TEST_BOOK);
        Assertions.assertThat(emBook).isEqualTo(NEW_TEST_BOOK);
    }

    @DisplayName("Get book by Id")
    @Test
    void getBookBookById() {
        Book actualBook = bookDaoJpa.findById(TEST_BOOK_FROM_DB.getId()).orElseThrow();
        Assertions.assertThat(actualBook).isEqualTo(TEST_BOOK_FROM_DB);
    }

    @DisplayName("Get all books")
    @Test
    void getAllBooks() {
        List<Book> actualBooks = bookDaoJpa.findAll();
        Assertions.assertThat(actualBooks).isEqualTo(TEST_BOOK_LIST_FROM_DB);
    }

    @DisplayName("Delete book by Id")
    @Test
    void deleteBookBookById() {
        long count = bookDaoJpa.count();
        bookDaoJpa.deleteById(DELETE_BOOK_ID);
        Assertions.assertThat(count).isEqualTo(bookDaoJpa.count() + 1);
    }

    //get book with incorrect id
    @DisplayName("Get book by incorrect Id")
    @Test
    void getBookByIncorrectId() {
        Assertions.assertThat(bookDaoJpa.findById(BOOK_INCORRECT_ID).isEmpty()).isTrue();
    }

    //delete book with incorrect id
    @DisplayName("Delet book by incorrect Id")
    @Test
    void deleteBookByIncorrectId() {
        long count = bookDaoJpa.count();
        bookDaoJpa.deleteById(BOOK_INCORRECT_ID); //no exception
        Assertions.assertThat(count).isEqualTo(bookDaoJpa.count());
    }
}
