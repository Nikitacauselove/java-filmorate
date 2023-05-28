package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final FilmDbStorage filmStorage;
    private static final Film FIRST_FILM = new Film("Film Updated", "New film update decription", LocalDate.parse("1989-04-17"), 190, Mpa.NC17);
    private static final Film SECOND_FILM = new Film("New film", "New film about friends", LocalDate.parse("1999-04-30"), 120, Mpa.NC17);

    @BeforeAll
    public static void beforeAll() {
        FIRST_FILM.setId(1);
        SECOND_FILM.setId(2);

        SECOND_FILM.setGenres(Set.of(Genre.COMEDY, Genre.DRAMA));
    }

    @Test
    public void findAll() {
        Assertions.assertEquals(filmStorage.findAll(), List.of(FIRST_FILM, SECOND_FILM));
    }

    @Test
    public void findFilmById() {
        Assertions.assertEquals(filmStorage.findFilmById(SECOND_FILM.getId()), SECOND_FILM);
    }
}
