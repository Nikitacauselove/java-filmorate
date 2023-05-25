package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmCounter = 0;

    private int getNextId() {
        return ++filmCounter;
    }

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film createFilm(Film film) {
        if (film.isValid()) {
            film.setId(getNextId());
            films.put(film.getId(), film);
        }
        return film;
    }

    public Film updateFilm(Film film) {
        if (film.isValid()) {
            if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
            }
        }
        return film;
    }

    public Film findFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
    }

    public void addLike(int id, int userId) {
        Film film = findFilmById(id);

        film.getLikingUsers().add(userId);
    }

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
