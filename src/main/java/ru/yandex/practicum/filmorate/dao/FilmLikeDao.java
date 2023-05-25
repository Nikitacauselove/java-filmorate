package ru.yandex.practicum.filmorate.dao;

import java.util.Set;

public interface FilmLikeDao {

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    Set<Integer> getLikingUsers(int filmId);
}
