package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

public interface FilmGenreDao {

    void addGenres(int filmId, Set<Genre> genres);

    void updateGenres(int filmId, Set<Genre> genres);

    Set<Genre> getGenres(int filmId);
}
