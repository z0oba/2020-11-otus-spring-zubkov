package ru.otus.homework.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.domain.Genre;

@DisplayName("Tests of genre repo")
@DataJpaTest
public class GenreRepositoryTest {

    private static final String NEW_GENRE_NAME = "Bestseller";
    private static final Genre TEST_GENRE_FROM_DB = new Genre(1L, "Story");
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Find genre by name from repository, genre exist at repo")
    @Test
    void findGenreByNameAuthorExist() {
        Assertions.assertThat(genreRepository.findByName(TEST_GENRE_FROM_DB.getName())).isEqualTo(TEST_GENRE_FROM_DB);
    }

    @DisplayName("Find genre by name at repository, genre is not exist at repo")
    @Test
    void findGenreByNameAuthorNotExist() {
        Assertions.assertThat(genreRepository.findByName(NEW_GENRE_NAME)).isNull();
    }

}
