package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        log.info("Получение списка всех пользователей.");

        return userStorage.findAll();
    }

    public User createUser(User user) {
        log.info("Создание пользователя.");

        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        log.info("Обновление пользователя.");

        return userStorage.updateUser(user);
    }

    public User findUser(int id) {
        log.info("Получение пользователя.");

        return userStorage.findUserById(id);
    }

    public void addFriend(int id, int friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void deleteFriend(int id, int friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public Collection<User> findAllFriends(int id) {
        return userStorage.findAllFriends(id);
    }

    public Collection<User> findCommonFriends(int id, int otherId) {
        return findAllFriends(id)
                .stream()
                .filter(findAllFriends(otherId)::contains)
                .collect(Collectors.toList());
    }
}
