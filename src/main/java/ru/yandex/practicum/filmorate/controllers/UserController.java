package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.Validator;

import javax.servlet.http.HttpServletRequest;
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
    public List<User> listCategories() {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                "GET", "/users");
        return List.copyOf(users.values());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                "POST", "/users");
        Validator.validationCheck(user);
        if (users.containsKey(user.getId())) {
            return null;
        }
        idCounter++;
        user.setId(idCounter);
        users.put(idCounter, user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                "PUT", "/users");
        Validator.validationCheck(user);
        int userId = user.getId();
        if (users.containsKey(userId)) {
            users.remove(userId);
            users.put(userId, user);
            return user;
        }
        throw new ValidationException("Такого idCounter не существует");
    }
}
