package ru.yandex.practicum.filmorate.controllers;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.Validator;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private int id = 0;
    private List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> filmsList(HttpServletRequest request) {
        infoLog(request);
        return films;
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film, HttpServletRequest request) {
        infoLog(request);
        Validator.validationCheck(film);
        for (Film oldFilm : films) {
            if (film.getId() == oldFilm.getId()) {
                return null;
            }
        }
        id++;
        film.setId(id);
        films.add(film);
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film, HttpServletRequest request) {
        infoLog(request);
        Validator.validationCheck(film);
        for (Film oldFilm : films) {
            if (oldFilm.getId() == film.getId()) {
                films.remove(oldFilm);
                films.add(film);
                return film;
            }
        }
        throw new ValidationException("Такого id не существует");
    }

    private void infoLog(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
    }
}
