package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserStorage storage, UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Пришел запрос / эндпоинт: '{} {}'", "GET", "/users");
        List<User> usersList = service.getUsers();
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/users", usersList);
        return usersList;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Пришел запрос / эндпоинт: '{} {}' с телом '{}", "POST", "/users", user);
        User savedUser = service.createUser(user);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "POST", "/users", savedUser);
        return savedUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Пришел запрос / эндпоинт: '{} {}' с телом '{}", "PUT", "/users", user);
        User updatedUser = service.updateUser(user);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "PUT", "/users", updatedUser);
        return updatedUser;
    }

    @DeleteMapping
    public void deleteUsers() {
        log.info("Пришел запрос / эндпоинт: '{} {}'", "DELETE", "/users");
        service.deleteUsers();
        log.info("Получен ответ / эндпоинт: '{} {}'", "DELETE", "/users");
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/users/" + id);
        User user = service.getUser(id);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/users/" + id, user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "PUT", "/users/" + id + "/friends/" + friendId);
        service.addFriend(id, friendId);
        log.info("Получен ответ / эндпоинт: '{} {}'", "PUT", "/users/" + id + "/friends/" + friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "DELETE", "/users/" + id + "/friends/" + friendId);
        service.deleteFriend(id, friendId);
        log.info("Получен ответ / эндпоинт: '{} {}'", "DELETE", "/users/" + id + "/friends/" + friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable long id) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/users/" + id + "/friends");
        List<User> users = service.getFriendsList(id);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/users/" + id + "/friends", users);
        return users;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Получен запрос / эндпоинт: '{} {}'", "GET", "/users/" + id + "/friends/common/" + otherId);
        List<User> users = service.getCommonFriends(id, otherId);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/users/" + id
                + "/friends/common/" + otherId, users);
        return users;
    }
}
