package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.properties.FilmGenre;

import java.util.List;

public interface GenreStorage {

    List<FilmGenre> getAllGenre();

    FilmGenre getGenreById(long id);
}
