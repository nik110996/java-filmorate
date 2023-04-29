package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
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
    public List<Film> getFilms() {
        log.info("Получен запрос / эндпоинт: '{} {}'",
                "GET", "/films");
        List<Film> filmsList = List.copyOf(films.values());
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}",
                "GET", "/films", filmsList);
        return filmsList;
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос / эндпоинт: '{} {}' с телом '{}",
                "POST", "/films", film);
        Validator.validationCheck(film);
        idCounter++;
        film.setId(idCounter);
        films.put(idCounter, film);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}",
                "POST", "/films", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос / эндпоинт: '{} {}' с телом '{}",
                "PUT", "/films", film);
        Validator.validationCheck(film);
        int filmId = film.getId();
        if (films.containsKey(filmId)) {
            films.put(filmId, film);
            log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}",
                    "PUT", "/films", film);
            return film;
        }
        throw new ValidationException("Такого idCounter не существует");
    }
}
