package ru.yandex.practicum.filmorate.dao;

import java.util.Set;

public interface FriendshipDao {

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    Set<Integer> getFriends(int userId);
}
