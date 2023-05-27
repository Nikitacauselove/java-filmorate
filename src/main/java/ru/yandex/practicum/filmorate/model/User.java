package ru.yandex.practicum.filmorate.model;

import lombok.*;

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
    private Set<Integer> friends = new HashSet<>();

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
}
