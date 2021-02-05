package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre,Long> {
    Genre findByName(String name);
}
