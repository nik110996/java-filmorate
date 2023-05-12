package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage storage;
    private final FilmService service;

    @Autowired
    public FilmController(FilmStorage storage, FilmService service) {
        this.storage = storage;
        this.service = service;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/films");
        List<Film> filmsList = storage.getFilms();
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/films", filmsList);
        return filmsList;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        return service.getFilm(id);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос / эндпоинт: '{} {}' с телом '{}", "POST", "/films", film);
        Film savedFilm = storage.createFilm(film);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "POST", "/films", savedFilm);
        return savedFilm;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос / эндпоинт: '{} {}' с телом '{}", "PUT", "/films", film);
        Film updatedFilm = storage.updateFilm(film);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "PUT", "/films", updatedFilm);
        return updatedFilm;
    }

    @DeleteMapping
    public void deleteFilms() {
        log.info("Получен запрос / эндпоинт: '{} {}'", "DELETE", "/films");
        storage.deleteFilms();
        log.info("Получен ответ / эндпоинт: '{} {}'", "DELETE", "/films");
    }

    @PutMapping("{id}/like/{userId}")
    public void putLike(@PathVariable long id, @PathVariable long userId) {
        service.putLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        service.deleteLike(id, userId);
    }

    @GetMapping("popular")
    public List<Film> getTop(@RequestParam(required = false) Long count) {
        return service.getTop(count);
    }

}
