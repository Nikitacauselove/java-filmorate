package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    private static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    private void validateFilm(Film film) {
        if (film.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название не может быть пустым.");
        }
        if (200 < film.getDescription().length()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(DATE_OF_FIRST_FILM)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Продолжительность фильма должна быть положительной.");
        }
    }

    public Collection<Film> findAll() {
        log.info("Получение всех фильмов.");

        return filmStorage.findAll();
    }

    public Film createFilm(Film film) {
        log.info("Добавление фильма.");

        validateFilm(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Обновление фильма.");

        validateFilm(film);
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
                .sorted()
                .limit(count)
                .collect(Collectors.toList());
    }
}
