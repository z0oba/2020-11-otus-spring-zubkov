package ru.otus.homework.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

@DisplayName("Tests of comment dao jpa")
@DataJpaTest
public class CommentRepositoryTest {

    private static final long INCORRECT_BOOK_ID = 100;
    private static final List<Comment> TEST_COMMENT_LIST_FROM_DB = List.of(
            new Comment(1L, "It`s ok!",
                    new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story"))),
            new Comment(2L, "Not bad",
                    new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems"))),
            new Comment(3L, "Awesome",
                    new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel")))
    );

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("Get comment by book Id, book exist")
    @Test
    void getCommentByBookIdBookExist() {
        Assertions.assertThat(commentRepository.findByBookId(TEST_COMMENT_LIST_FROM_DB.get(0).getBook().getId()).get(0))
                .isEqualTo(TEST_COMMENT_LIST_FROM_DB.get(0));
    }

    @DisplayName("Get comment by book Id, book not exist")
    @Test
    void getCommentByBookIdBookNotExist() {
        Assertions.assertThat(commentRepository.findByBookId(INCORRECT_BOOK_ID)).isEmpty();
    }

    @DisplayName("Get all comments")
    @Test
    void getAllComments() {
        Assertions.assertThat(commentRepository.findAll()).isEqualTo(TEST_COMMENT_LIST_FROM_DB);
    }
}
