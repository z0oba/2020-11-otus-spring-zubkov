package ru.otus.homework.repo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.homework.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for special comment repo methods")
@DataMongoTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    private final String TEST_COMMENT = "Test comment";

    @Test
    @DisplayName("Find comment by text")
    void findByTextTest() {
        //save comment to db
        commentRepository.save(new Comment(TEST_COMMENT)).subscribe();

        //read comment from db
        StepVerifier.create(commentRepository.findByText(TEST_COMMENT))
                .assertNext(comment -> {
                    assertThat(comment.getText()).isEqualTo(TEST_COMMENT);
                })
                .expectComplete()
                .verify();
    }
}
