package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmCounter = 0;

    private int getNextId() {
        return ++filmCounter;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
        return film;
    }

    @Override
    public Film findFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
    }

    @Override
    public void addLike(int id, int userId) {
        Film film = findFilmById(id);

        film.getLikingUsers().add(userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        Film film = findFilmById(id);
        Set<Integer> likingUsers = film.getLikingUsers();

        if (likingUsers.contains(userId)) {
            likingUsers.remove(userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
    }
}
