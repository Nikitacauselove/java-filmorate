package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("mpa")
@Slf4j
public class MpaController {

    @GetMapping
    public Collection<Mpa> findAll() {
        return Arrays.asList(Mpa.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpa> findMpa(@PathVariable int id) {
        return ResponseEntity.ok(Mpa.findMpa(id));
    }
}
