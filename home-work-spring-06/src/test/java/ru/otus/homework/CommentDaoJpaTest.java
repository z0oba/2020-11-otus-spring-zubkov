package ru.otus.homework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.dao.BookDaoJpa;
import ru.otus.homework.dao.CommentDao;
import ru.otus.homework.dao.CommentDaoJpa;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

@DisplayName("Tests of comment dao jpa")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(CommentDaoJpa.class)
public class CommentDaoJpaTest {

    private static final long EXPECTED_COMMENT_COUNT = 3;
    private static final String TEST_COMMENT_TEXT = "TEST_COMMENT_TEXT";
    private static final long TEST_COMMENT_ID = 4;
    private static final long DELETE_COMMENT_ID = 2;
    private static final long COMMENT_INCORRECT_ID = 100;

    private static final Book TEST_BOOK_FROM_DB = new Book(1L, "Conservancy area",
            new Author(1L, "Dovlatov"), new Genre(1L, "Story"));

    private static final Book NEW_TEST_BOOK = new Book(1L, "1984",
            new Author(1L, "Orwell"), new Genre(3L, "Story"));

    private static final Comment TEST_COMMENT_FROM_DB = new Comment(1L, "It`s ok!",
            TEST_BOOK_FROM_DB);

    private static final List<Comment> TEST_COMMENT_LIST_FROM_DB = List.of(
            new Comment(1L, "It`s ok!",
                    new Book(1L, "Conservancy area", new Author(1L, "Dovlatov"), new Genre(1L, "Story"))),
            new Comment(2L, "Not bad",
                    new Book(2L, "Mtsyri", new Author(2L, "Lermontov"), new Genre(2L, "Poems"))),
            new Comment(3L, "Awesome",
                    new Book(3L, "Master and Margarita", new Author(3L, "Bulgakov"), new Genre(3L, "Novel")))
    );

    @Autowired
    private CommentDao commentDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Get correct comment count from db")
    @Test
    void bookCount() {
        Assertions.assertThat(commentDaoJpa.count()).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @DisplayName("Insert new comment into DB book has already presents at db")
    @Test
    void insertComment() {
        //new comment
        Comment comment = new Comment(TEST_COMMENT_TEXT, TEST_BOOK_FROM_DB);
        commentDaoJpa.save(comment);
        Comment actualComment = commentDaoJpa.findById(TEST_COMMENT_ID).orElseThrow();
        Comment emComment = em.find(Comment.class, TEST_COMMENT_ID);

        Assertions.assertThat(actualComment).isEqualTo(comment);
        Assertions.assertThat(emComment).isEqualTo(comment);
    }

    @DisplayName("Get comment by comment Id")
    @Test
    void getCommentById() {
        Comment actualComment = commentDaoJpa.findById(TEST_COMMENT_FROM_DB.getId()).orElseThrow();
        Assertions.assertThat(actualComment).isEqualTo(TEST_COMMENT_FROM_DB);
    }

    @DisplayName("Get comment by book Id")
    @Test
    void getCommentByBookId() {
        Comment actualComment = commentDaoJpa.findByBookId(TEST_COMMENT_FROM_DB.getBook().getId()).get(0);
        Assertions.assertThat(actualComment).isEqualTo(TEST_COMMENT_FROM_DB);
    }


    @DisplayName("Get all comments")
    @Test
    void getAllComments() {
        List<Comment> actualComments = commentDaoJpa.findAll();
        Assertions.assertThat(actualComments).isEqualTo(TEST_COMMENT_LIST_FROM_DB);
    }

    @DisplayName("Delete comment by Id")
    @Test
    void deleteCommentById() {
        long count = commentDaoJpa.count();
        commentDaoJpa.deleteById(DELETE_COMMENT_ID);
        Assertions.assertThat(count).isEqualTo(commentDaoJpa.count() + 1);
    }

    //get comment with incorrect id
    @DisplayName("Get comment by incorrect Id")
    @Test
    void getBookByIncorrectId() {
        Assertions.assertThat(commentDaoJpa.findById(COMMENT_INCORRECT_ID).isEmpty()).isTrue();
    }

    //delete comment with incorrect id
    @DisplayName("Delete comment by incorrect Id")
    @Test
    void deleteBookByIncorrectId() {
        long count = commentDaoJpa.count();
        commentDaoJpa.deleteById(COMMENT_INCORRECT_ID); //no exception
        Assertions.assertThat(count).isEqualTo(commentDaoJpa.count());
    }

}
