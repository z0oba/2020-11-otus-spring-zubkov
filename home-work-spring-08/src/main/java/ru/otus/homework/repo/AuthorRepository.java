package ru.otus.homework.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, Long> {
    Author findByName(String name);
}
