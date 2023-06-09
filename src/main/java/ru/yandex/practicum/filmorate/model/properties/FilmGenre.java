package ru.yandex.practicum.filmorate.model.properties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilmGenre {
    private long id;
    private String name;


    public FilmGenre(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
