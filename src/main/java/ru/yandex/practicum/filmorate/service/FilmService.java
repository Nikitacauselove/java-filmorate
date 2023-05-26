package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> findAll() {
        log.info("Получение всех фильмов.");

        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        log.info("Добавление фильма.");

        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Обновление фильма.");

        return filmStorage.updateFilm(film);
    }

    public Film findFilm(int id) {
        log.info("Получение фильма.");

        return filmStorage.findFilmById(id);
    }

    public void addLike(int id, int userId) {
        filmStorage.addLike(id, userId);
    }

    public void deleteLike(int id, int userId) {
        filmStorage.deleteLike(id, userId);
    }

    public Collection<Film> findPopularFilms(int count) {
        return findAll()
                .stream()
                .sorted(Film.FILM_COMPARATOR.reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
