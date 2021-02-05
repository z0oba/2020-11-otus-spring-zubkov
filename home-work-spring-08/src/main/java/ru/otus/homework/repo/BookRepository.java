package ru.otus.homework.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book,Long> {
    List<Book> findAll();
}
