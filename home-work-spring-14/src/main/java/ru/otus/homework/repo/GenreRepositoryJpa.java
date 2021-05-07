package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.dto.GenreJpa;

public interface GenreRepositoryJpa extends JpaRepository<GenreJpa, Long> {
    GenreJpa findByName(String name);
}
