package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();

    private static int filmCounter = 0;

    private int getUserId() {
        return ++filmCounter;
    }

    @GetMapping
    public List<Film> getAllUsers() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public ResponseEntity<Film> add(@RequestBody Film film) throws ValidationException {
        if (film.isValid()) {
            film.setId(getUserId());
            films.put(film.getId(), film);
            return ResponseEntity.ok(film);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<Film> update(@RequestBody Film film) throws ValidationException {
        if (film.isValid() && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return ResponseEntity.ok(film);
        } else {
            return ResponseEntity.internalServerError().body(film);
        }
    }
}
