package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

class UserTest {

    @Test
    public void invalidEmail() {
        User user = new User("mail.ru", "dolore ullamco", "", LocalDate.parse("1980-08-20"));

        Assertions.assertThrows(ResponseStatusException.class, user::isValid);
    }

    @Test
    public void invalidLogin() {
        User user = new User("yandex@mail.ru", "dolore ullamco", null, LocalDate.parse("2446-08-20"));

        Assertions.assertThrows(ResponseStatusException.class, user::isValid);
    }

    @Test
    public void invalidBirthday() {
        User user = new User("test@mail.ru", "dolore", "", LocalDate.parse("2446-08-20"));

        Assertions.assertThrows(ResponseStatusException.class, user::isValid);
    }
}