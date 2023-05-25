package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.dao.FilmLikeDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmDaoImpl implements FilmDao {
    private final FilmLikeDao filmLikeDao;
    private final FilmGenreDao filmGenreDao;
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(FilmLikeDao filmLikeDao, FilmGenreDao filmGenreDao, JdbcTemplate jdbcTemplate) {
        this.filmLikeDao = filmLikeDao;
        this.filmGenreDao = filmGenreDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Film> findAll() {
        List<Integer> listOfId = jdbcTemplate.queryForList("select film_id from films order by film_id", Integer.class);

        return listOfId.stream()
                .map(this::findFilmById)
                .collect(Collectors.toList());
    }

    public void createFilm(Film film) {
        String sql = "insert into films (film_id, name, description, release_date, duration, mpa_id) values (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        filmGenreDao.addGenres(film.getId(), film.getGenres());
    }

    public void updateFilm(Film film) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", film.getId());

        if (rs.next()) {
            String sql = "update films set film_id = ?, name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? where film_id = ?";

            jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
            filmGenreDao.updateGenres(film.getId(), film.getGenres());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
    }

    public Film findFilmById(int id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", id);

        if (rs.next()) {
            Film film = new Film(rs.getString("name"), rs.getString("description"), rs.getDate("release_date").toLocalDate(), rs.getInt("duration"), Mpa.findMpa(rs.getInt("mpa_id")));
            film.setId(id);
            film.setGenres(filmGenreDao.getGenres(id));
            film.setLikingUsers(filmLikeDao.getLikingUsers(id));
            return film;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Фильм с указанным идентификатором не найден.");
        }
    }
}
