package ru.yandex.practicum.filmorate.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.Validator;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private int id = 0;
    private List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> listCategories(HttpServletRequest request) {
        infoLog(request);
        return users;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user, HttpServletRequest request) {
        infoLog(request);
        Validator.validationCheck(user);
        for (User oldUser : users) {
            if (user.getId() == oldUser.getId()) {
                return null;
            }
        }
        id++;
        user.setId(id);
        users.add(user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user, HttpServletRequest request) {
        infoLog(request);
        Validator.validationCheck(user);
        for (User oldUser : users) {
            if (user.getId() == oldUser.getId()) {
                users.remove(oldUser);
                users.add(user);
                return user;
            }
        }
        throw new ValidationException("Такого id не существует");
    }

    private void infoLog(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}'",
                request.getMethod(), request.getRequestURI());
    }
}
