package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public long count() {
        return jdbcOperations.queryForObject("select count(id) from books", new HashMap<>(1), Long.class);
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("author_id", book.getAuthor().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into books (name,genre_id,author_id) values(:name,:genre_id,:author_id) on duplicate key update name = :name", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbcOperations.queryForObject(
                "select book.id, book.name, book.genre_id, book.author_id, author.name authorName, genre.name genreName " +
                        "from (books book join authors author on book.author_id = author.id) " +
                        "join genres genre on book.genre_id = genre.id " +
                        "where book.id = :id",
                params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("select book.id, book.name, book.genre_id, book.author_id, author.name authorName, genre.name genreName " +
                        "from (books book join authors author on book.author_id = author.id) " +
                        "join genres genre on book.genre_id = genre.id",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("authorName"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genreName"));
            return new Book(id, name, author, genre);
        }
    }
}
