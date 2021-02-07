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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Tests of comment service")
@SpringBootTest
public class CommentsServiceImplTest {

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
            new Book(0L, "Conservancy area", authors.get(0), genres.get(0), new ArrayList<>(comments.subList(0, 1))),
            new Book(1L, "Mtsyri", authors.get(1), genres.get(1), comments.subList(1, 2)),
            new Book(2L, "Master and Margarita", authors.get(2), genres.get(2), comments.subList(0, 2))
    );

    private static final long EXPECTED_COMMENT_COUNT = 3;
    private static final long TEST_BOOK_ID = 5;
    private static final Book TEST_BOOK_FROM_DB = books.get(0);
    private static final Book NEW_TEST_BOOK = new Book(TEST_BOOK_ID, "1984", authors.get(0), genres.get(0), comments);
    private static final List<Book> TEST_BOOK_LIST_FROM_DB = books;

    private static final Comment TEST_COMMENT_FROM_DB = comments.get(0);
    private static final List<Comment> TEST_COMMENT_LIST_FROM_DB = comments;
    private static final Comment NEW_TEST_COMMENT = new Comment(4L, "Test comment");

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private CommentService commentService;

    @DisplayName("Get comments count by commentservice")
    @Test
    void commentCount() {
        given(commentRepository.count()).willReturn(EXPECTED_COMMENT_COUNT);
        Assertions.assertThat(commentRepository.count()).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @DisplayName("Get comment by id with commentService")
    @Test
    void getCommentById() {
        given(commentRepository.findById(TEST_COMMENT_FROM_DB.getId())).willReturn(Optional.of(TEST_COMMENT_FROM_DB));
        Assertions.assertThat(commentService.getById(TEST_COMMENT_FROM_DB.getId())).isEqualTo(TEST_COMMENT_FROM_DB);
    }

    @DisplayName("Get all comments by id with commentService")
    @Test
    void getAllComments() {
        given(commentRepository.findAll()).willReturn(TEST_COMMENT_LIST_FROM_DB);
        Assertions.assertThat(commentService.getAll()).isEqualTo(TEST_COMMENT_LIST_FROM_DB);
    }

    @DisplayName("Insert cooment by commentService")
    @Test
    void insertComment() {

        given(bookRepository.findById(TEST_BOOK_FROM_DB.getId())).willReturn(Optional.of(TEST_BOOK_FROM_DB));
        given(commentRepository.save(any(Comment.class))).willReturn(NEW_TEST_COMMENT);

        Assertions.assertThat(commentService.insert(
                TEST_BOOK_FROM_DB.getId(),
                NEW_TEST_COMMENT.getText()))
                .isEqualTo(NEW_TEST_COMMENT.getId());
    }

    @DisplayName("Delete comment by id with commentService")
    @Test
    void deleteBookById() {
        commentService.deleteById(TEST_COMMENT_FROM_DB.getId());
        given(commentRepository.count()).willReturn(EXPECTED_COMMENT_COUNT - 1);
        Assertions.assertThat(commentService.count()).isEqualTo(EXPECTED_COMMENT_COUNT - 1);
        given(commentRepository.findById(TEST_COMMENT_FROM_DB.getId())).willReturn(null);
    }
}
