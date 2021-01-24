package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public long count() {
        return jdbcOperations.queryForObject("select count(id) from authors", new HashMap<>(1), Long.class);
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author_name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //check author for exist at db
        jdbcOperations.update("insert ignore into authors (name) values(:author_name)", params, keyHolder);
        if (keyHolder.getKey() != null)
            return keyHolder.getKey().longValue();
        else
            return jdbcOperations.queryForObject("select id from authors where name=:author_name", params, Long.class);

    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbcOperations.queryForObject(
                "select id, name from authors where id = :id",
                params, new AuthorDaoJdbc.AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("select id, name from authors", new AuthorDaoJdbc.AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }
}
