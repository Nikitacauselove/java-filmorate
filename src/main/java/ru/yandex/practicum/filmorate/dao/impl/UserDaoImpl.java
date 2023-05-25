package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<User> findAll() {
        List<Integer> listOfId = jdbcTemplate.queryForList("select user_id from users", Integer.class);

        return listOfId.stream()
                .map(this::findUserById)
                .collect(Collectors.toList());
    }

    public void createUser(User user) {
        String sql = "insert into users (user_id, email, login, name, birthday) values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
    }

    public void updateUser(User user) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", user.getId());

        if (rs.next()) {
            String sql = "update users set user_id = ?, email = ?, login = ?, name = ?, birthday = ?";

            jdbcTemplate.update(sql, user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
    }

    public User findUserById(int id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", id);

        if (rs.next()) {
            User user = new User(rs.getString("email"), rs.getString("login"), rs.getString("name"), rs.getDate("birthday").toLocalDate());

            user.setId(id);
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
    }
}
