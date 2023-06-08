package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    @Autowired
    @Qualifier("userDbStorage")
    private final UserStorage storage;

    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public User getUser(long id) {
        if (storage.findUserById(id) == null) {
            throw new UserNotFoundException("Такого пользователя не существует");
        }
        return storage.findUserById(id);
    }

    public void addFriend(long id, long friendId) {
        storage.addFriend(id, friendId);
    }

    public void deleteFriend(long id, long friendId) {
        storage.deleteFriend(id, friendId);
    }

    public List<User> getFriendsList(long id) {
        return storage.getFriendsList(id);
    }

    public List<User> getUsers() {
        return storage.getUsers();
    }

    public User createUser(User user) {
        return storage.createUser(user);
    }

    public User updateUser(User user) {
        return storage.updateUser(user);
    }

    public void deleteUsers() {
        storage.deleteUsers();
    }

    public List<User> getCommonFriends(long id, long otherId) {
        return storage.getCommonFriends(id, otherId);
    }
}

