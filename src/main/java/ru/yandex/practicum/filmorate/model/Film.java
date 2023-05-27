package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
public class Film implements Comparable<Film> {
    private int id;
    @NotNull
    private final String name;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
    private final int duration;
    @NotNull
    private final Mpa mpa;
    private Set<Genre> genres = new TreeSet<>();
    private Set<Integer> likingUsers = new HashSet<>();

    @Override
    public int compareTo(Film film) {
        return film.getLikingUsers().size() - this.getLikingUsers().size();
    }
}
