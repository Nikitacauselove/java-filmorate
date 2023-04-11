package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();

    private static int userCounter = 0;

    private int getUserId() {
        return ++userCounter;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user) throws ValidationException {
        if (user.isValid()) {
            user.setId(getUserId());
            users.put(user.getId(), user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) throws ValidationException {
        if (user.isValid() && users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.internalServerError().body(user);
        }
    }
}
