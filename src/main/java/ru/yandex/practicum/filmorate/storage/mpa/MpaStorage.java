package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.properties.RatingMPA;

import java.util.List;

public interface MpaStorage {

    List<RatingMPA> getRating();

    RatingMPA getRatingById(long id);
}
