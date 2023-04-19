package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userCounter = 0;

    private int getNewUserId() {
        return ++userCounter;
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public User createUser(User user) {
        if (user.isValid()) {
            user.setId(getNewUserId());
            users.put(user.getId(), user);
        }
        return user;
    }

    public User updateUser(User user) {
        if (user.isValid()) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
            }
        }
        return user;
    }
}
