package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.properties.FilmGenre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    @Qualifier("genreDbStorage")
    private final GenreStorage storage;

    public GenreService(GenreStorage storage) {
        this.storage = storage;
    }

    public List<FilmGenre> getAllGenre() {
        return storage.getAllGenre();
    }

    public FilmGenre getGenreById(long id) {
        return storage.getGenreById(id);
    }
}
