package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreDao {

    void addGenres(int filmId, List<Genre> genres);

    void updateGenres(int filmId, List<Genre> genres);

    List<Genre> getGenres(int filmId);
}
