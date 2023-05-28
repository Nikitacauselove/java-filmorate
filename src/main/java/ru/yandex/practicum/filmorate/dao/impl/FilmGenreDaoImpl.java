package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void deleteGenres(int filmId) {
        jdbcTemplate.update("delete from film_genre where film_id = ?", filmId);
    }

    @Override
    public void addGenres(int filmId, Set<Genre> genres) {
        for (Genre genre : genres) {
            SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from film_genre where film_id = ? and genre_id = ?", filmId, genre.getId());

            if (!rs.next()) {
                jdbcTemplate.update("insert into film_genre (film_id, genre_id) values (?, ?)", filmId, genre.getId());
            }
        }
    }

    @Override
    public void updateGenres(int filmId, Set<Genre> genres) {
        deleteGenres(filmId);
        addGenres(filmId, genres);
    }

    @Override
    public Set<Genre> getGenres(int filmId) {
        List<Integer> listOfId =  jdbcTemplate.queryForList("select genre_id from film_genre where film_id = ?", Integer.class, filmId);

        return listOfId.stream()
                .map(Genre::findGenre)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
