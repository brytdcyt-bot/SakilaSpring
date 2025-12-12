package com.pluralsight.SakilaSpring.dao;

import com.pluralsight.SakilaSpring.models.Film;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcFilmDao implements IFilmDao {

    private final DataSource dataSource;

    public JdbcFilmDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Film film) {
        final String sql = """
                INSERT INTO film (title, rental_rate, language_id)
                VALUES (?, ?, ?)
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, film.getTitle());
            stmt.setDouble(2, film.getRentalRate());
            stmt.setInt(3, 1); // placeholder language_id

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert film", e);
        }
    }

    @Override
    public List<Film> findAll() {
        final String sql = """
                SELECT film_id, title, rental_rate
                FROM film
            """;

        List<Film> films = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setRentalRate(rs.getDouble("rental_rate"));
                films.add(film);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve films", e);
        }

        return films;
    }

    @Override
    public Optional<Film> findById(int id) {
        final String sql = """
                SELECT film_id, title, rental_rate
                FROM film
                WHERE film_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Film film = new Film();
            film.setFilmId(rs.getInt("film_id"));
            film.setTitle(rs.getString("title"));
            film.setRentalRate(rs.getDouble("rental_rate"));

            return Optional.of(film);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve film with id " + id, e);
        }
    }

    @Override
    public boolean update(Film film) {
        final String sql = """
                UPDATE film
                SET title = ?, rental_rate = ?
                WHERE film_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, film.getTitle());
            stmt.setDouble(2, film.getRentalRate());
            stmt.setInt(3, film.getFilmId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update film with id " + film.getFilmId(), e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM film WHERE film_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete film with id " + id, e);
        }
    }
}
