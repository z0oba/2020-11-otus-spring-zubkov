package ru.otus.homework.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

@DisplayName("Tests of comment dao jpa")
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentRepositoryTest {

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

    private static final long EXPECTED_COMMENT_COUNT = 3;
    private static final long DELETE_COMMENT_ID = 2;
    private static final long COMMENT_INCORRECT_ID = 100;

    private static final Book TEST_BOOK_FROM_DB = books.get(0);
    private static final Comment TEST_COMMENT_FROM_DB = comments.get(0);
    private static final Comment NEW_TEST_COMMENT_FROM_DB = new Comment(3L, "Test comment");
    private static final List<Comment> TEST_COMMENT_LIST_FROM_DB = comments;

    @Autowired
    private CommentRepository commentRepository;


    @DisplayName("Get correct comment count from db")
    @Test
    void bookCount() {
        Assertions.assertThat(commentRepository.count()).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @DisplayName("Insert new comment into DB book has already presents at db")
    @Test
    void insertComment() {
        commentRepository.save(NEW_TEST_COMMENT_FROM_DB);
        Assertions.assertThat(commentRepository.findById(NEW_TEST_COMMENT_FROM_DB.getId()).orElseThrow())
                .isEqualTo(NEW_TEST_COMMENT_FROM_DB);
    }

    @DisplayName("Get comment by comment Id")
    @Test
    void getCommentById() {
        Assertions.assertThat(commentRepository.findById(TEST_COMMENT_FROM_DB.getId()).orElseThrow())
                .isEqualTo(TEST_COMMENT_FROM_DB);
    }

    @DisplayName("Get all comments")
    @Test
    void getAllComments() {
        Assertions.assertThat(commentRepository.findAll()).isEqualTo(TEST_COMMENT_LIST_FROM_DB);
    }

    @DisplayName("Delete comment by Id")
    @Test
    void deleteCommentById() {
        long count = commentRepository.count();
        commentRepository.deleteById(DELETE_COMMENT_ID);
        Assertions.assertThat(count).isEqualTo(commentRepository.count() + 1);
    }

    //get comment with incorrect id
    @DisplayName("Get comment by incorrect Id")
    @Test
    void getCommentByIncorrectId() {
        Assertions.assertThat(commentRepository.findById(COMMENT_INCORRECT_ID).isEmpty()).isTrue();
    }

    //delete comment with incorrect id
    @DisplayName("Delete comment by incorrect Id")
    @Test
    void deleteCommentByIncorrectId() {
        long count = commentRepository.count();
        commentRepository.deleteById(COMMENT_INCORRECT_ID);
        Assertions.assertThat(count).isEqualTo(commentRepository.count());
    }

}
