package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int userCounter = 0;

    private int getUserId() {
        return ++userCounter;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) throws ResponseStatusException {
        if (user.isValid()) {
            user.setId(getUserId());
            users.put(user.getId(), user);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) throws ResponseStatusException {
        if (user.isValid()) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
            }
        }
        return ResponseEntity.ok(user);
    }
}
