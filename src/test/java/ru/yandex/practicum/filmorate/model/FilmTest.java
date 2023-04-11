package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Test
    public void invalidName() {
        Film film = new Film("", "Description", LocalDate.parse("1900-03-25"), 200);

        assertThrows(ValidationException.class, film::isValid);
    }

    @Test
    public void invalidDescription() {
        Film film = new Film("Film name", "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.", LocalDate.parse("1900-03-25"), 200);

        assertThrows(ValidationException.class, film::isValid);
    }

    @Test
    public void invalidReleaseDate() {
        Film film = new Film("Name", "Description", LocalDate.parse("1890-03-25"), 200);

        assertThrows(ValidationException.class, film::isValid);
    }

    @Test
    public void invalidDuration() {
        Film film = new Film("Name", "Descrition", LocalDate.parse("1980-03-25"), -200);

        assertThrows(ValidationException.class, film::isValid);
    }

}