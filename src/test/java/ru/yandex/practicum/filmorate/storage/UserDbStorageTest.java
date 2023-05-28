package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final UserDbStorage userStorage;
    private static final User FIRST_USER = new User("mail@yandex.ru", "doloreUpdate", "est adipisicing", LocalDate.parse("1976-09-20"));
    private static final User SECOND_USER = new User("friend@mail.ru", "friend", "friend adipisicing", LocalDate.parse("1976-08-20"));
    private static final User THIRD_USER = new User("friend@common.ru", "common", "common", LocalDate.parse("2000-08-20"));

    @BeforeAll
    public static void beforeAll() {
        FIRST_USER.setId(1);
        SECOND_USER.setId(2);
        THIRD_USER.setId(3);

        FIRST_USER.setFriends(Set.of(3));
        SECOND_USER.setFriends(Set.of(3));
    }

    @Test
    public void findAll() {
        Assertions.assertEquals(userStorage.findAll(), List.of(FIRST_USER, SECOND_USER, THIRD_USER));
    }

    @Test
    public void findUserById() {
        Assertions.assertEquals(userStorage.findUserById(FIRST_USER.getId()), FIRST_USER);
    }

    @Test
    public void findAllFriends() {
        Assertions.assertEquals(userStorage.findAllFriends(FIRST_USER.getId()), List.of(THIRD_USER));
    }
}
