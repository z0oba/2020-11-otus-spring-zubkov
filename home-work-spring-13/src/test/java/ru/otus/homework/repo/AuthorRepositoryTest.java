package ru.otus.homework.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.domain.Author;

@DisplayName("Tests of author repo")
@DataJpaTest
public class AuthorRepositoryTest {

    private static final String NEW_AUTHOR_NAME = "Nekrasov";
    private static final Author TEST_AUTHOR_FROM_DB = new Author(1L, "Dovlatov");
    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Find author by name from repository, author exist at repo")
    @Test
    void findAuthorByNameAuthorExist() {
        Assertions.assertThat(authorRepository.findByName(TEST_AUTHOR_FROM_DB.getName())).isEqualTo(TEST_AUTHOR_FROM_DB);
    }

    @DisplayName("Find author by name at repository")
    @Test
    void findAuthorByNameAuthorNotExist() {
        Assertions.assertThat(authorRepository.findByName(NEW_AUTHOR_NAME)).isNull();
    }

}
