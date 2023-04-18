package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int filmCounter = 0;

    private int getUserId() {
        return ++filmCounter;
    }

    @GetMapping
    public List<Film> getAllUsers() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) throws ResponseStatusException {
        if (film.isValid()) {
            film.setId(getUserId());
            films.put(film.getId(), film);
        }
        return ResponseEntity.ok(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) throws ResponseStatusException {
        if (film.isValid()) {
            if (films.containsKey(film.getId())) {
                films.put(film.getId(), film);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
            }
        }
        return ResponseEntity.ok(film);
    }
}
