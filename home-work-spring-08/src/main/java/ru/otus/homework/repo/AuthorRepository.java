package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Author;

public interface AuthorRepository extends MongoRepository<Author,Long> {
    Author findByName(String name);
}
