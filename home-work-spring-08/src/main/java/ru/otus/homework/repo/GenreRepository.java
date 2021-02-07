package ru.otus.homework.repo;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByName(String name);
}
