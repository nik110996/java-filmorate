package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.properties.RatingMPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
public class MpaController {

    private final MpaService service;

    public MpaController(MpaService service) {
        this.service = service;
    }

    @GetMapping("/mpa")
    public List<RatingMPA> getRating() {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/mpa");
        List<RatingMPA> ratingList = service.getRating();
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/mpa", ratingList);
        return ratingList;
    }

    @GetMapping("/mpa/{id}")
    public RatingMPA getRatingById(@PathVariable long id) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/mpa/" + id);
        RatingMPA rating = service.getRatingById(id);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/mpa/" + id, rating);
        return rating;
    }
}
