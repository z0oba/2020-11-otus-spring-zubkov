package ru.otus.homework.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@DisplayName("Tests of comment service")
@SpringBootTest(classes = CommentServiceImp.class)
public class CommentsServiceImplTest {

    private static final long EXPECTED_COMMENT_COUNT = 3;
    private static final long TEST_COMMENT_ID = 1;

    private static final Book TEST_BOOK_FROM_DB = new Book(1L, "Conservancy area",
            new Author(1L, "Dovlatov"), new Genre(1L, "Story"));

    private static final Comment TEST_COMMENT_FROM_DB = new Comment(1L, "It`s ok!",
            TEST_BOOK_FROM_DB);

    private static final Comment NEW_TEST_COMMENT = new Comment(4L, "Test comment",
            TEST_BOOK_FROM_DB);

    private static final List<Comment> TEST_COMMENT_LIST_FROM_DB = List.of(
            new Comment(1L, "It`s ok!",
                    new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story"))),
            new Comment(2L, "Not bad",
                    new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems"))),
            new Comment(3L, "Awesome",
                    new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel")))
    );

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
                NEW_TEST_COMMENT.getBook().getId(),
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
