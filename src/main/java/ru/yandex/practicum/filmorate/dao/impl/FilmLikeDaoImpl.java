package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FilmLikeDaoImpl implements FilmLikeDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmLikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(int filmId, int userId) {
        jdbcTemplate.update("insert into film_like (film_id, user_id) values (?, ?)", filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from film_like where film_id = ? and user_id = ?", filmId, userId);

        if (rs.next()) {
            jdbcTemplate.update("delete from film_like where film_id = ? and user_id = ?", filmId, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден.");
        }
    }

    public Set<Integer> getLikingUsers(int filmId) {
        List<Integer> listOfId = jdbcTemplate.queryForList("select user_id from film_like where film_id = ?", Integer.class, filmId);

        return new HashSet<>(listOfId);
    }
}
