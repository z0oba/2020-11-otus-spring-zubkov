package ru.otus.homework.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c join fetch c.book")
    List<Comment> findAll();

    @Query("select c from Comment c where c.book.id=:id")
    List<Comment> findByBookId(@Param("id")long bookId);
}
