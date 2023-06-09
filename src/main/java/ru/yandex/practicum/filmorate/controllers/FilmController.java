package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/films");
        List<Film> filmsList = service.getFilms();
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/films", filmsList);
        return filmsList;
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable long id) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/films/" + id);
        Film film = service.getFilm(id);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/films/" + id, film);
        return film;
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос / эндпоинт: '{} {}' с телом '{}", "POST", "/films", film);
        Film savedFilm = service.createFilm(film);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "POST", "/films", savedFilm);
        return savedFilm;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос / эндпоинт: '{} {}' с телом '{}", "PUT", "/films", film);
        Film updatedFilm = service.updateFilm(film);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "PUT", "/films", updatedFilm);
        return updatedFilm;
    }

    @DeleteMapping("/films")
    public void deleteFilms() {
        log.info("Получен запрос / эндпоинт: '{} {}'", "DELETE", "/films");
        service.deleteFilms();
        log.info("Получен ответ / эндпоинт: '{} {}'", "DELETE", "/films");
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void putLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "PUT", "/films/" + id);
        service.addLike(id, userId);
        log.info("Получен ответ / эндпоинт: '{} {}'", "PUT", "/films/" + id + "/like/" + userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "DELETE", "/films/" + id);
        service.deleteLike(id, userId);
        log.info("Получен ответ / эндпоинт: '{} {}'", "DELETE", "/films/" + id + "/like/" + userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopular(@RequestParam(required = false, defaultValue = "10") Long count) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/films/popular " + "с параметром " + count);
        List<Film> films = service.getPopular(count);
        log.info("Получен ответ / эндпоинт: '{} {}'", "GET", "/films/popular " + "с параметром "
                + count + " и с телом " + films);
        return films;
    }
}
