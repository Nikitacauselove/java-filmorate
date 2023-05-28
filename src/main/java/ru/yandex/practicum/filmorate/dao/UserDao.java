package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserDao {

    Collection<User> findAll();

    void createUser(User user);

    void updateUser(User user);

    User findUserById(int id);
}
