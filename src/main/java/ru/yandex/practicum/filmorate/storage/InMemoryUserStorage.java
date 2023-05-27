package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userCounter = 0;

    private int getNextId() {
        return ++userCounter;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User createUser(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
        return user;
    }

    @Override
    public User findUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
    }

    @Override
    public void addFriend(int id, int friendId) {
        User user = findUserById(id);
        User friend = findUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        User user = findUserById(id);
        User friend = findUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    @Override
    public Collection<User> findAllFriends(int id) {
        return findUserById(id).getFriends()
                .stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }
}
