package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private Film film;
    private Set<Long> likes;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film getFilm(long id) {
        if (filmStorage.findFilmById(id) == null) {
            throw new FilmNotFoundException("Такого фильма не существует");
        }
        return filmStorage.findFilmById(id);
    }

    public void putLike(long id, long userId) {
        getFilmAndLikesList(id, userId);
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(userId);
        film.setLikes(likes);
    }

    public void deleteLike(long id, long userId) {
        getFilmAndLikesList(id, userId);
        likes.remove(userId);
    }

    public List<Film> getTop(Long count) {
        List<Film> films = sortedFilms();
        if (count == null || count == 0) {
            count = 10L;
        }
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


    private void getFilmAndLikesList(long id, long userId) {
        if (userStorage.findUserById(userId) == null) {
            throw new UserNotFoundException("Такого пользователя не существует");
        }
        if (filmStorage.findFilmById(id) == null) {
            throw new FilmNotFoundException("Такого фильма не существует");
        }
        film = filmStorage.findFilmById(id);
        likes = film.getLikes();
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
