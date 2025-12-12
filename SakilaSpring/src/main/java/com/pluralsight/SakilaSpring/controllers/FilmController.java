package com.pluralsight.SakilaSpring.controllers;

import com.pluralsight.SakilaSpring.dao.IFilmDao;
import com.pluralsight.SakilaSpring.models.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final IFilmDao filmDao;

    public FilmController(IFilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmDao.getAll();
        return ResponseEntity.ok(films);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        Film film = filmDao.findById(id);
        if (film == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(film);
    }

    @PostMapping
    public ResponseEntity<Void> createFilm(@RequestBody Film film) {
        filmDao.add(film);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable int id) {
        filmDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}