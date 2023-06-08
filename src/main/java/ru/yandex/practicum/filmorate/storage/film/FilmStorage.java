package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.properties.FilmGenre;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;

import java.util.List;

public interface FilmStorage {

    List<Film> getFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film findFilmById(long id);

    void deleteFilms();

    void addLike(long id, long userId);

    void deleteLike(long id, long userId);

    List<FilmGenre> getAllGenre();

    FilmGenre getGenreById(long id);

    List<RatingMPA> getRating();

    RatingMPA getRatingById(long id);
}
