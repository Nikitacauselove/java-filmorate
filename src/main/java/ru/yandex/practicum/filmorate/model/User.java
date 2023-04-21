package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<Integer> friends = new HashSet<>();

    public User(final String email, final String login, final String name, final LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (name == null || name.isEmpty()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

    public boolean isValid() {
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
