package ru.otus.homework.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.homework.domain.Book;
import ru.otus.homework.mongock.changelog.DatabaseChangelog;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookControllerTest {

    private final Book TEST_BOOK = new Book("test_book_name", "test_author", "test_genre");

    @Autowired
    private BookController bookController;

    private WebTestClient webTestClient;

    @BeforeEach
    private void initWebTestClient() {
        webTestClient = WebTestClient
                .bindToController(bookController)
                .build();
    }

    @Test
    @DisplayName("get request /books")
    public void getBooksTest() {

        webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class).isEqualTo(DatabaseChangelog.BOOKS);
    }

    @Test
    @DisplayName("put request /books/{bookId}")
    public void putBookTest() {

        webTestClient.put()
                .uri(String.format("/books/%s", DatabaseChangelog.BOOKS.get(0).getId()))
                .body(BodyInserters.fromValue(TEST_BOOK))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(Book.class).value(book -> {
            assertThat(book.getAuthor()).isEqualTo(TEST_BOOK.getAuthor());
            assertThat(book.getComments()).isEqualTo(TEST_BOOK.getComments());
            assertThat(book.getName()).isEqualTo(TEST_BOOK.getName());
            assertThat(book.getId()).isEqualTo(DatabaseChangelog.BOOKS.get(0).getId());
        });
    }
}