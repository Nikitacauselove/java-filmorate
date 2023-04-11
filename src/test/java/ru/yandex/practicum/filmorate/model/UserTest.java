package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void invalidEmail() {
        User user = new User("mail.ru", "dolore ullamco", LocalDate.parse("1980-08-20"));

        assertThrows(ValidationException.class, user::isValid);
    }

    @Test
    public void invalidLogin() {
        User user = new User("yandex@mail.ru", "dolore ullamco", LocalDate.parse("2446-08-20"));

        assertThrows(ValidationException.class, user::isValid);
    }

    @Test
    public void invalidBirthday() {
        User user = new User("test@mail.ru", "dolore", LocalDate.parse("2446-08-20"));

        assertThrows(ValidationException.class, user::isValid);
    }

}