package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.dto.AuthorJpa;
import ru.otus.homework.dto.BookJpa;
import ru.otus.homework.dto.GenreJpa;

import java.util.List;

public interface BookRepositoryJpa extends JpaRepository<BookJpa, Long> {
    @EntityGraph(value = "author_genre_entity_graph")
    List<BookJpa> findAll();

    List<BookJpa> findByName(String name);

    BookJpa findByNameAndGenreAndAuthor(String name, GenreJpa genre, AuthorJpa author);
}
