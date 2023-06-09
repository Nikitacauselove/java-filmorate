package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("genres")
@Slf4j
public class GenresController {

    @GetMapping
    public Collection<Genre> findAll() {
        return Arrays.asList(Genre.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> findGenre(@PathVariable int id) {
        return ResponseEntity.ok(Genre.findGenre(id));
    }
}
