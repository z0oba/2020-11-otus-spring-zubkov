package ru.otus.homework.repo;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findByName(String name);
}
