package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmCounter = 0;

    private int getNewFilmId() {
        return ++filmCounter;
    }

    public Collection<Film> findAll() {
        return films.values();
    }

    public Film createFilm(Film film) {
        if (film.isValid()) {
            film.setId(getNewFilmId());
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
}
