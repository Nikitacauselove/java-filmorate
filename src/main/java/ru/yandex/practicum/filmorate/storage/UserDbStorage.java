package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final FriendshipDao friendshipDao;
    private final UserDao userDao;
    private int userCounter = 0;

    public UserDbStorage(FriendshipDao friendshipDao, UserDao userDao) {
        this.friendshipDao = friendshipDao;
        this.userDao = userDao;
    }

    private int getNextId() {
        return ++userCounter;
    }

    @Override
    public Collection<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User createUser(User user) {
        if (user.isValid()) {
            user.setId(getNextId());
            userDao.createUser(user);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.isValid()) {
            userDao.updateUser(user);
        }
        return user;
    }

    @Override
    public User findUserById(int id) {
        User user = userDao.findUserById(id);

        user.setFriends(friendshipDao.getFriends(id));
        return userDao.findUserById(id);
    }

    @Override
    public void addFriend(int id, int friendId) {
        User friend = findUserById(friendId);

        friendshipDao.addFriend(id, friend.getId());
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        User user = findUserById(id);

        friendshipDao.deleteFriend(user.getId(), friendId);
    }

    @Override
    public Collection<User> findAllFriends(int id) {
        return friendshipDao.getFriends(id)
                .stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }
}
