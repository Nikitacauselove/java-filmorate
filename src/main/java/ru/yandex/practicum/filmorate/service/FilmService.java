package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private static final Comparator<Film> FILM_COMPARATOR = Comparator.comparing(film -> film.getLikingUsers().size());

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film findFilm(int id) {
        return filmStorage.findFilmById(id);
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.findFilmById(id);

        film.getLikingUsers().add(userId);
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.findFilmById(id);
        Set<Integer> likingUsers = film.getLikingUsers();

        if (likingUsers.contains(userId)) {
            likingUsers.remove(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
    }

    public Collection<Film> findPopularFilms(int count) {
        return findAll()
                .stream()
                .sorted(FILM_COMPARATOR.reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
