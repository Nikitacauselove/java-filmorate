package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User findUser(int id) {
        return userStorage.findUserById(id);
    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    public Collection<User> findAllFriends(int id) {
        return userStorage.findUserById(id).getFriends()
                .stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> findCommonFriends(int id, int otherId) {
        return findAllFriends(id)
                .stream()
                .filter(findAllFriends(otherId)::contains)
                .collect(Collectors.toList());
    }
}
