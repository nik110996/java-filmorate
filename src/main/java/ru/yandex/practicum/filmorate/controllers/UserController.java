package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private int idCounter = 0;
    private Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        log.info("Пришел запрос / эндпоинт: '{} {}'",
                "GET", "/users");
        List<User> userList = List.copyOf(users.values());
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}",
                "GET", "/users", userList);
        return List.copyOf(users.values());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Пришел запрос / эндпоинт: '{} {}' с телом '{}",
                "POST", "/users", user);
        Validator.validationCheck(user);
        idCounter++;
        user.setId(idCounter);
        users.put(idCounter, user);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}",
                "POST", "/users", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Пришел запрос / эндпоинт: '{} {}' с телом '{}",
                "PUT", "/users", user);
        Validator.validationCheck(user);
        int userId = user.getId();
        if (users.containsKey(userId)) {
            users.put(userId, user);
            log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}",
                    "PUT", "/users", user);
            return user;
        }
        throw new ValidationException("Такого idCounter не существует");
    }
}
