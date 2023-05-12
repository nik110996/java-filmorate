package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserStorage storage;
    private User user;
    private User secondUser;
    private Set<Long> userFriends;
    private Set<Long> secondUserFriends;


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
        getUsersAndFriendsList(id, friendId);
        if (userFriends == null) {
            userFriends = new HashSet<>();
        }
        if (secondUserFriends == null) {
            secondUserFriends = new HashSet<>();
        }

        userFriends.add(friendId);
        user.setFriends(userFriends);
        secondUserFriends.add(id);
        secondUser.setFriends(secondUserFriends);
    }

    public void deleteFriend(long id, long friendId) {
        getUsersAndFriendsList(id, friendId);
        if (userFriends != null && userFriends.contains(friendId)) {
            userFriends.remove(friendId);
            user.setFriends(userFriends);
            secondUserFriends.remove(id);
            secondUser.setFriends(secondUserFriends);
            return;
        }
        throw new UserNotFoundException("Пользователи не являются друзьями");
    }

    public List<User> getFriendsList(long id) {
        User user = storage.findUserById(id);
        Set<Long> userFriends = user.getFriends();
        if (userFriends == null || userFriends.isEmpty()) {
            throw new UserNotFoundException("Список друзей пуст");
        }
        List<User> friendsList = new ArrayList<>();
        for (Long friendId : userFriends) {
            friendsList.add(storage.findUserById(friendId));
        }
        return friendsList;
    }

    public List<User> getCommonFriends(long id, long otherId) {
        getUsersAndFriendsList(id, otherId);
        List<User> commonFriends = new ArrayList<>();
        if (secondUserFriends == null || userFriends == null) {
            return commonFriends;
        }

        for (Long userId : userFriends) {
            if (secondUserFriends.contains(userId)) {
                commonFriends.add(storage.findUserById(userId));
            }
        }
        return commonFriends;
    }

    private void getUsersAndFriendsList(long id, long secondId) {
        if (storage.findUserById(id) == null || storage.findUserById(secondId) == null) {
            throw new UserNotFoundException("Такого пользователя / пользователей - не существует ");
        }
        user = storage.findUserById(id);
        secondUser = storage.findUserById(secondId);
        userFriends = user.getFriends();
        secondUserFriends = secondUser.getFriends();
    }
}
