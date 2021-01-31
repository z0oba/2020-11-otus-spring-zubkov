package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        return null;
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre findByName(String name) {
        try {
            TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name=:name", Genre.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Genre g " +
                "where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
