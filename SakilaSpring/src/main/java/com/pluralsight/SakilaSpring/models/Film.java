package com.pluralsight.SakilaSpring.models;

import java.util.Objects;

public class Film {

    private Integer filmId;
    private String title;
    private Double rentalRate;

    public Film() {
    }

    public Film(Integer filmId, String title, Double rentalRate) {
        this.filmId = filmId;
        this.title = title;
        this.rentalRate = rentalRate;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(Double rentalRate) {
        this.rentalRate = rentalRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film film)) return false;
        return Objects.equals(filmId, film.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", rentalRate=" + rentalRate +
                '}';
    }
}