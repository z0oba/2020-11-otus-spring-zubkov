package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a where a.name = :name")
    Author findByName(@Param("name") String name);
}
