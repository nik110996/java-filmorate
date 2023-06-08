package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.properties.FilmGenre;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class FilmService {
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film getFilm(long id) {
        Film film = filmStorage.findFilmById(id);
        if (film == null) {
            throw new FilmNotFoundException("Такого фильма не существует");
        }
        return film;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void deleteFilms() {
        filmStorage.deleteFilms();
    }

    public void addLike(long id, long userId) {
        checkUserAndFilmExisting(id, userId);
        filmStorage.addLike(id, userId);
    }

    public void deleteLike(long id, long userId) {
        checkUserAndFilmExisting(id, userId);
        Film film = filmStorage.findFilmById(id);
        film.removeLike(userId);
    }

    public List<Film> getPopular(Long count) {
        List<Film> films = sortedFilms();
        List<Film> sortedTop = new LinkedList<>();
        int k = 0;
        for (Film film : films) {
            sortedTop.add(film);
            k++;
            if (k == count) {
                break;
            }
        }
        return sortedTop;
    }

    public List<FilmGenre> getAllGenre() {
        return filmStorage.getAllGenre();
    }

    public FilmGenre getGenreById(long id) {
        return filmStorage.getGenreById(id);
    }

    public List<RatingMPA> getRating() {
        return filmStorage.getRating();
    }

    public RatingMPA getRatingById(long id) {
        return filmStorage.getRatingById(id);
    }

    private void checkUserAndFilmExisting(long id, long userId) {
        if (userStorage.findUserById(userId) == null) {
            throw new UserNotFoundException("Такого пользователя не существует");
        }
        if (filmStorage.findFilmById(id) == null) {
            throw new FilmNotFoundException("Такого фильма не существует");
        }
    }

    private List<Film> sortedFilms() {
        List<Film> films = filmStorage.getFilms();
        films.sort(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                int first = 0;
                int second = 0;
                if (o1.getLikes() != null) {
                    first = o1.getLikes().size();
                }
                if (o2.getLikes() != null) {
                    second = o2.getLikes().size();
                }
                return second - first;
            }
        });
        return films;
    }
}
