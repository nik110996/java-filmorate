package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Film {

    private int id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
}
