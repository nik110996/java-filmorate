package ru.yandex.practicum.filmorate.model.properties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingMPA {

    private long id;
    private String name;

    public RatingMPA(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
