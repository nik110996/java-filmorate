package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.properties.FilmGenre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
public class GenreController {

    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping("/genres")
    public List<FilmGenre> getAllGenre() {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/genres");
        List<FilmGenre> filmsGenres = service.getAllGenre();
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/genres", filmsGenres);
        return filmsGenres;
    }

    @GetMapping("/genres/{id}")
    public FilmGenre getGenreById(@PathVariable long id) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/genres/" + id);
        FilmGenre genre = service.getGenreById(id);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/genres/" + id, genre);
        return genre;
    }
}
