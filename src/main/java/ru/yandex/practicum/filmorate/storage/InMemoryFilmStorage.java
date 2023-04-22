package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
                throw new FilmNotFoundException();
            }
        }
        return film;
    }

    public Film findFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new FilmNotFoundException();
        }
    }
}
