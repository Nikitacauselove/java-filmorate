package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;

    private static final LocalDate DATE_OF_FIRST_FILM = LocalDate.of(1895, 12, 28);

    public boolean isValid() throws ValidationException {
        if (name.isEmpty()) {
            throw new ValidationException("Название не может быть пустым.");
        }
        if (200 < description.length()) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if (releaseDate.isBefore(DATE_OF_FIRST_FILM)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (duration < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }
        return true;
    }
}
