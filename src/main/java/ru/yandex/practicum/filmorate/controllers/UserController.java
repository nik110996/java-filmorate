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

    private final UserStorage storage;
    private final UserService service;

    @Autowired
    public UserController(UserStorage storage, UserService service) {
        this.storage = storage;
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Пришел запрос / эндпоинт: '{} {}'", "GET", "/users");
        List<User> usersList = storage.getUsers();
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "GET", "/users", usersList);
        return usersList;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Пришел запрос / эндпоинт: '{} {}' с телом '{}", "POST", "/users", user);
        User savedUser = storage.createUser(user);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "POST", "/users", savedUser);
        return savedUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Пришел запрос / эндпоинт: '{} {}' с телом '{}", "PUT", "/users", user);
        User updatedUser = storage.updateUser(user);
        log.info("Получен ответ / эндпоинт: '{} {}' с телом '{}", "PUT", "/users", updatedUser);
        return updatedUser;
    }

    @DeleteMapping
    public void deleteUsers() {
        log.info("Пришел запрос / эндпоинт: '{} {}'", "DELETE", "/users");
        storage.deleteUsers();
        log.info("Получен ответ / эндпоинт: '{} {}'", "DELETE", "/users");
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        //return storage.findUserById(id);
        return service.getUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendsList(@PathVariable long id) {
        return service.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return service.getCommonFriends(id, otherId);
    }
}
