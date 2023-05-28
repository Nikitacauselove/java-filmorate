package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Film> findAll() {
        List<Integer> listOfId = jdbcTemplate.queryForList("select film_id from films order by film_id", Integer.class);

        return listOfId.stream()
                .map(this::findFilmById)
                .collect(Collectors.toList());
    }

    @Override
    public void createFilm(Film film) {
        String sql = "insert into films (film_id, name, description, release_date, duration, mpa_id) values (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
    }

    @Override
    public void updateFilm(Film film) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", film.getId());

        if (rs.next()) {
            String sql = "update films set film_id = ?, name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? where film_id = ?";

            jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
    }

    @Override
    public Film findFilmById(int id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", id);

        if (rs.next()) {
            Film film = new Film(rs.getString("name"), rs.getString("description"), rs.getDate("release_date").toLocalDate(), rs.getInt("duration"), Mpa.findMpa(rs.getInt("mpa_id")));

            film.setId(id);
            return film;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
    }
}
