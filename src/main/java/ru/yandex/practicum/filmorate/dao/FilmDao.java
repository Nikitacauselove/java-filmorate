package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmDao {

    Collection<Film> findAll();

    void createFilm(Film film);

    void updateFilm(Film film);

    Film findFilmById(int id);
}
