package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(value = "author_genre_entity_graph")
    List<Book> findAll();

    @Query("select b from Book b where b.name = :name")
    List<Book> findByName(@Param("name") String name);
}
