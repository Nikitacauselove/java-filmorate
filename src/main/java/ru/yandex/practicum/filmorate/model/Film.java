package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotNull
    private final String name;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
    private final int duration;

    private static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);

    public boolean isValid() throws ResponseStatusException {
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Название не может быть пустым.");
        }
        if (200 < description.length()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Максимальная длина описания — 200 символов.");
        }
        if (releaseDate.isBefore(DATE_OF_FIRST_FILM)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (duration < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Продолжительность фильма должна быть положительной.");
        }
        return true;
    }
}