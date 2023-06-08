package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.properties.FilmGenre;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 0;


    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        Validator.validationCheck(film);
        idCounter++;
        film.setId(idCounter);
        films.put(idCounter, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        Validator.validationCheck(film);
        long filmId = film.getId();
        if (films.containsKey(filmId)) {
            films.put(filmId, film);
            return film;
        }
        throw new ValidationException("Такого idCounter не существует");
    }

    @Override
    public Film findFilmById(long id) {
        return films.get(id);
    }

    @Override
    public void deleteFilms() {
        films.clear();
    }

    @Override
    public void addLike(long id, long userId) {
        Film film = films.get(id);
        film.addLike(userId);
    }

    @Override
    public void deleteLike(long id, long userId) {
        Film film = films.get(id);
        film.removeLike(userId);
    }

    @Override
    public List<FilmGenre> getAllGenre() {
        return null;
    }

    @Override
    public FilmGenre getGenreById(long id) {
        return null;
    }

    @Override
    public List<RatingMPA> getRating() {
        return null;
    }

    @Override
    public RatingMPA getRatingById(long id) {
        return null;
    }
}
