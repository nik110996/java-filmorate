package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    @Autowired
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    @Qualifier("friendDbStorage")
    private final FriendStorage friendStorage;

    public UserService(UserStorage storage, FriendStorage storage1) {
        this.userStorage = storage;
        this.friendStorage = storage1;
    }

    public User getUser(long id) {
        if (userStorage.findUserById(id) == null) {
            throw new UserNotFoundException("Такого пользователя не существует");
        }
        return userStorage.findUserById(id);
    }


    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User createUser(User user) {
        Validator.validationCheck(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        Validator.validationCheck(user);
        return userStorage.updateUser(user);
    }

    public void deleteUsers() {
        userStorage.deleteUsers();
    }

    public List<User> getCommonFriends(long id, long otherId) {
        return friendStorage.getCommonFriends(id, otherId);
    }

    public void addFriend(long id, long friendId) {
        friendStorage.addFriend(id, friendId);
    }

    public void deleteFriend(long id, long friendId) {
        friendStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriendsList(long id) {
        return friendStorage.getFriendsList(id);
    }
}

