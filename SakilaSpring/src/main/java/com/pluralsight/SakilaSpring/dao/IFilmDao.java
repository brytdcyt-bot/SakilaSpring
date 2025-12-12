package com.pluralsight.SakilaSpring.dao;

import com.pluralsight.SakilaSpring.models.Film;

import java.util.List;
import java.util.Optional;

public interface IFilmDao {

    /**
     * Create a new Film record.
     */
    void add(Film film);

    /**
     * Retrieve all films.
     */
    List<Film> findAll();

    /**
     * Find a film by its ID.
     * Returns Optional.empty() if not found.
     */
    Optional<Film> findById(int id);

    /**
     * Update an existing film.
     * Returns true if updated, false if the film does not exist.
     */
    boolean update(Film film);

    /**
     * Delete a film by ID.
     * Returns true if deleted, false if no film matched the ID.
     */
    boolean deleteById(int id);
}