package ru.otus.homework.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.homework.domain.Book;
import ru.otus.homework.repo.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Some book controller tests")
@SpringBootTest
public class BookControllerTest {

    private final Book testBook = new Book("test_book_name", "test_author", "test_genre");

    @Autowired
    private BookController bookController;

    @Autowired
    private BookRepository bookRepository;

    private WebTestClient webTestClient;

    private List<Book> expectedBooks;

    @BeforeEach
    private void initWebTestClient() {
        webTestClient = WebTestClient
                .bindToController(bookController)
                .build();
    }

    @BeforeEach
    private void getExpectedBookListFromRepo() {
        expectedBooks = bookRepository.findAll().collectList().block();
    }

    @Test
    @DisplayName("get request /books")
    public void getBooksTest() {

        webTestClient.get().uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class).value(books -> {
            int counter = 0;
            for (Book book : books) {
                assertThat(book.getId()).isEqualTo(expectedBooks.get(counter).getId());
                assertThat(book.getName()).isEqualTo(expectedBooks.get(counter).getName());
                assertThat(book.getAuthor()).isEqualTo(expectedBooks.get(counter).getAuthor());
                assertThat(book.getGenre()).isEqualTo(expectedBooks.get(counter).getGenre());
                ++counter;
            }
        });
    }

    @Test
    @DisplayName("put request /books/{bookId}")
    public void putBookTest() {

        webTestClient.put()
                .uri(String.format("/books/%s", expectedBooks.get(0).getId()))
                .body(BodyInserters.fromValue(testBook))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(Book.class).value(book -> {
            assertThat(book.getAuthor()).isEqualTo(testBook.getAuthor());
            assertThat(book.getComments()).isEqualTo(testBook.getComments());
            assertThat(book.getName()).isEqualTo(testBook.getName());
            assertThat(book.getId()).isEqualTo(expectedBooks.get(0).getId());
        });
    }
}