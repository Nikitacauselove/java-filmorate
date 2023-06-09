package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> findAll();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film findFilmById(int id);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);
}
