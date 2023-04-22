package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userCounter = 0;

    private int getNextId() {
        return ++userCounter;
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public User createUser(User user) {
        if (user.isValid()) {
            user.setId(getNextId());
            users.put(user.getId(), user);
        }
        return user;
    }

    public User updateUser(User user) {
        if (user.isValid()) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            } else {
                throw new UserNotFoundException();
            }
        }
        return user;
    }

    public User findUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new UserNotFoundException();
        }
    }
}
