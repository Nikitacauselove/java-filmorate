package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final FilmDao filmDao;
    private final FilmLikeDao filmLikeDao;
    private int filmCounter = 0;

    public FilmDbStorage(FilmDao filmDao, FilmLikeDao filmLikeDao) {
        this.filmDao = filmDao;
        this.filmLikeDao = filmLikeDao;
    }

    private int getNextId() {
        return ++filmCounter;
    }

    public Collection<Film> findAll() {
        return filmDao.findAll();
    }

    public Film createFilm(Film film) {
        if (film.isValid()) {
            film.setId(getNextId());
            filmDao.createFilm(film);
        }
        return findFilmById(film.getId());
    }

    public Film updateFilm(Film film) {
        if (film.isValid()) {
            filmDao.updateFilm(film);
        }
        return findFilmById(film.getId());
    }

    public Film findFilmById(int id) {
        return filmDao.findFilmById(id);
    }

    public void addLike(int id, int userId) {
        Film film = findFilmById(id);

        filmLikeDao.addLike(id, userId);
        film.getLikingUsers().add(userId);
    }

    public void deleteLike(int id, int userId) {
        Film film = findFilmById(id);
        Set<Integer> likingUsers = film.getLikingUsers();

        filmLikeDao.deleteLike(id, userId);
        likingUsers.remove(userId);
    }
}
