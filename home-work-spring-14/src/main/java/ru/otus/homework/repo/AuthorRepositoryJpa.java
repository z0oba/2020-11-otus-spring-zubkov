package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.dto.AuthorJpa;

public interface AuthorRepositoryJpa extends JpaRepository<AuthorJpa, Long> {
    AuthorJpa findByName(String name);
}
