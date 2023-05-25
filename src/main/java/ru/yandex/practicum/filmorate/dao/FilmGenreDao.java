package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.TreeSet;

public interface FilmGenreDao {

    void addGenres(int filmId, TreeSet<Genre> genres);

    void updateGenres(int filmId, TreeSet<Genre> genres);

    TreeSet<Genre> getGenres(int filmId);
}
