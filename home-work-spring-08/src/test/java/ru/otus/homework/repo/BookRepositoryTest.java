package ru.otus.homework.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

@DisplayName("Tests of book dao")
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryTest {

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
    private static final long TEST_BOOK_ID = 4;
    private static final long DELETE_BOOK_ID = 2;
    private static final long BOOK_INCORRECT_ID = 100;
    private static final Book TEST_BOOK_FROM_DB = books.get(0);
    private static final Book NEW_TEST_BOOK = new Book(TEST_BOOK_ID, "1984", authors.get(0), genres.get(0), comments);
    private static final List<Book> TEST_BOOK_LIST_FROM_DB = books;
    private static final List<Book> TEST_BOOK_LIST_FROM_DB_WITH_COMMENT = List.of(books.get(0), books.get(2));

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Get correct book count from db")
    @Test
    void bookCount() {
        Assertions.assertThat(bookRepository.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Insert new book into DB, author and genre presents at db")
    @Test
    void insertBook() {
        bookRepository.save(NEW_TEST_BOOK);
        Assertions.assertThat(bookRepository.findById(NEW_TEST_BOOK.getId()).orElseThrow()).isEqualTo(NEW_TEST_BOOK);
    }

    @DisplayName("Get book by Id")
    @Test
    void getBookBookById() {
        Assertions.assertThat(bookRepository.findById(TEST_BOOK_FROM_DB.getId()).orElseThrow())
                .isEqualTo(TEST_BOOK_FROM_DB);
    }

    @DisplayName("Get all books")
    @Test
    @Order(Integer.MAX_VALUE)
    void getAllBooks() {
        List<Book> actualBooks = bookRepository.findAll();
        Assertions.assertThat(actualBooks).isEqualTo(TEST_BOOK_LIST_FROM_DB);
    }

    @DisplayName("Delete book by Id")
    @Test
    void deleteBookBookById() {
        long count = bookRepository.count();
        bookRepository.deleteById(DELETE_BOOK_ID);
        Assertions.assertThat(count).isEqualTo(bookRepository.count() + 1);
    }

    @DisplayName("Get book by incorrect Id")
    @Test
    void getBookByIncorrectId() {
        Assertions.assertThat(bookRepository.findById(BOOK_INCORRECT_ID).isEmpty()).isTrue();
    }

    @DisplayName("Delete book by incorrect Id")
    @Test
    void deleteBookByIncorrectId() {
        long count = bookRepository.count();
        bookRepository.deleteById(BOOK_INCORRECT_ID);
        Assertions.assertThat(count).isEqualTo(bookRepository.count());
    }

    @DisplayName("Find books by author")
    @Test
    void findBookByAuthor() {
        Assertions.assertThat(bookRepository.findBooksByAuthor(TEST_BOOK_FROM_DB.getAuthor()))
                .isEqualTo(List.of(TEST_BOOK_FROM_DB));
    }

    @DisplayName("Find books by author")
    @Test
    void findBookByUnknownAuthor() {
        Assertions.assertThat(bookRepository.findBooksByAuthor(TEST_BOOK_FROM_DB.getAuthor()))
                .isEqualTo(List.of(TEST_BOOK_FROM_DB));
    }

    @DisplayName("Find books by author")
    @Test
    void findBookByGenre() {
        Assertions.assertThat(bookRepository.findBooksByGenre(TEST_BOOK_FROM_DB.getGenre()))
                .isEqualTo(List.of(TEST_BOOK_FROM_DB));
    }

    @DisplayName("Find books by comment")
    @Test
    void findBookByComment() {
        Assertions.assertThat(bookRepository.findBooksByComment(TEST_BOOK_FROM_DB.getComments().get(0)))
                .isEqualTo(TEST_BOOK_LIST_FROM_DB_WITH_COMMENT);
    }
}
