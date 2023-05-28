package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final FilmDao filmDao;
    private final FilmLikeDao filmLikeDao;
    private final FilmGenreDao filmGenreDao;
    private int filmCounter = 0;

    public FilmDbStorage(FilmDao filmDao, FilmLikeDao filmLikeDao, FilmGenreDao filmGenreDao) {
        this.filmDao = filmDao;
        this.filmLikeDao = filmLikeDao;
        this.filmGenreDao = filmGenreDao;
    }

    private int getNextId() {
        return ++filmCounter;
    }

    @Override
    public Collection<Film> findAll() {
        Collection<Film> collectionOfFilms = filmDao.findAll();

        collectionOfFilms.forEach(film -> {
            film.setGenres(filmGenreDao.getGenres(film.getId()));
            film.setLikingUsers(filmLikeDao.getLikingUsers(film.getId()));
        });
        return collectionOfFilms;
    }

    @Override
    public Film createFilm(Film film) {
        if (film.isValid()) {
            film.setId(getNextId());
            filmDao.createFilm(film);
            filmGenreDao.addGenres(film.getId(), film.getGenres());
        }
        return findFilmById(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.isValid()) {
            filmDao.updateFilm(film);
            filmGenreDao.updateGenres(film.getId(), film.getGenres());
        }
        return findFilmById(film.getId());
    }

    @Override
    public Film findFilmById(int id) {
        Film film =  filmDao.findFilmById(id);

        film.setGenres(filmGenreDao.getGenres(id));
        film.setLikingUsers(filmLikeDao.getLikingUsers(id));
        return film;
    }

    @Override
    public void addLike(int id, int userId) {
        Film film = findFilmById(id);

        filmLikeDao.addLike(id, userId);
        film.getLikingUsers().add(userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        Film film = findFilmById(id);
        Set<Integer> likingUsers = film.getLikingUsers();

        filmLikeDao.deleteLike(id, userId);
        likingUsers.remove(userId);
    }
}
