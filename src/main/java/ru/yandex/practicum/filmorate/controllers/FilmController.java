package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.Validator;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private int idCounter = 0;
    private Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> filmsList() {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                "GET", "/films");
        return List.copyOf(films.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                "POST", "/films");
        Validator.validationCheck(film);
        if (films.containsKey(film.getId())) {
            return null;
        }
        idCounter++;
        film.setId(idCounter);
        films.put(idCounter, film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                "PUT", "/films");
        Validator.validationCheck(film);
        int filmId = film.getId();
        if (films.containsKey(filmId)) {
            films.remove(filmId);
            films.put(filmId, film);
            return film;
        }
        throw new ValidationException("Такого idCounter не существует");
    }
}
