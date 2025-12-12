package com.pluralsight.SakilaSpring.dao;

import com.pluralsight.SakilaSpring.models.Film;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Primary // Prefer this over JdbcFilmDao unless overridden
public class SimpleFilmDao implements IFilmDao {

    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public SimpleFilmDao() {
        add(new Film(0, "The Matrix", 2.99));
        add(new Film(0, "Finding Nemo", 0.99));
        add(new Film(0, "Titanic", 3.99));
    }

    @Override
    public void add(Film film) {
        int id = nextId.getAndIncrement();
        film.setFilmId(id);
        films.put(id, film);
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public boolean update(Film film) {
        int id = film.getFilmId();
        if (!films.containsKey(id)) {
            return false;
        }
        films.put(id, film);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return films.remove(id) != null;
    }
}