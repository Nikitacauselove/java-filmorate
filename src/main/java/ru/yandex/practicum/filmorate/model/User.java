package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotNull
    private final String email;
    @NotNull
    private final String login;
    private final String name;
    @NotNull
    private final LocalDate birthday;

    public User(final String email, final String login, final LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = login;
        this.birthday = birthday;
    }

    public boolean isValid() throws ResponseStatusException {
        if (!email.contains("@") || email.contains(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (login.contains(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Логин не может быть пустым и содержать пробелы.");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Дата рождения не может быть в будущем.");
        }
        return true;
    }

}
