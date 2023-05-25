package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendshipDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FriendshipDaoImpl implements FriendshipDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(int userId, int friendId) {
        jdbcTemplate.update("insert into friendship (requesting_user_id, receiving_user_id) values (?, ?)", userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        jdbcTemplate.update("delete from friendship where requesting_user_id = ? and receiving_user_id = ?", userId, friendId);
    }

    public Set<Integer> getFriends(int userId) {
        List<Integer> listOfId =  jdbcTemplate.queryForList("select receiving_user_id from friendship where requesting_user_id = ?", Integer.class, userId);

        return new HashSet<>(listOfId);
    }
}
