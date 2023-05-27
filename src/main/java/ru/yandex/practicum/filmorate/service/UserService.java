package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
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

    private void validateUser(User user) {
        if (!user.getEmail().contains("@") || user.getEmail().contains(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().contains(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Дата рождения не может быть в будущем.");
        }
    }

    public Collection<User> findAll() {
        log.info("Получение списка всех пользователей.");

        return userStorage.findAll();
    }

    public User createUser(User user) {
        log.info("Создание пользователя.");

        validateUser(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        log.info("Обновление пользователя.");

        validateUser(user);
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
