package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService( UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(long id) {
        /*if (storage.findUserById(id) == null) {
            throw new UserNotFoundException("Такого пользователя не существует");
        }
        return storage.findUserById(id);*/
        return null;
    }

    public void addFriend(long id, long friendId) {
        /*checkUsersExisting(id, friendId);
        User user = storage.findUserById(id);
        User secondUser = storage.findUserById(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> secondUserFriends = secondUser.getFriends();
        if (userFriends == null) {
            userFriends = new HashSet<>();
        }
        if (secondUserFriends == null) {
            secondUserFriends = new HashSet<>();
        }

        userFriends.add(friendId);
        user.setFriends(userFriends);
        secondUserFriends.add(id);
        secondUser.setFriends(secondUserFriends);*/
    }

    public void deleteFriend(long id, long friendId) {
        /*checkUsersExisting(id, friendId);
        User user = storage.findUserById(id);
        User secondUser = storage.findUserById(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> secondUserFriends = secondUser.getFriends();
        if (userFriends != null && userFriends.contains(friendId)) {
            userFriends.remove(friendId);
            user.setFriends(userFriends);
            secondUserFriends.remove(id);
            secondUser.setFriends(secondUserFriends);
            return;
        }
        throw new UserNotFoundException("Пользователи не являются друзьями");*/
    }

    public List<User> getFriendsList(long id) {
       /* User user = storage.findUserById(id);
        Set<Long> userFriends = user.getFriends();
        if (userFriends == null || userFriends.isEmpty()) {
            throw new UserNotFoundException("Список друзей пуст");
        }
        List<User> friendsList = new ArrayList<>();
        for (Long friendId : userFriends) {
            friendsList.add(storage.findUserById(friendId));
        }
        return friendsList;*/
        return null;
    }

    public List<User> getUsers() {
       // return storage.getUsers();
        return null;
    }

    public User createUser(User user) {
       return userDao.createUser(user);
       // return storage.createUser(user);
    }

    public User updateUser(User user) {
       // return storage.updateUser(user);
        return null;
    }

    public void deleteUsers() {
        //storage.deleteUsers();
    }

    public List<User> getCommonFriends(long id, long otherId) {
       /* checkUsersExisting(id, otherId);
        User user = storage.findUserById(id);
        User secondUser = storage.findUserById(otherId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> secondUserFriends = secondUser.getFriends();
        List<User> commonFriends = new ArrayList<>();
        if (secondUserFriends == null || userFriends == null) {
            return commonFriends;
        }

        for (Long userId : userFriends) {
            if (secondUserFriends.contains(userId)) {
                commonFriends.add(storage.findUserById(userId));
            }
        }
        return commonFriends;*/
        return null;
    }

    private void checkUsersExisting(long id, long secondId) {
        /*if (storage.findUserById(id) == null || storage.findUserById(secondId) == null) {
            throw new UserNotFoundException("Такого пользователя / пользователей - не существует ");
        }*/
    }
}
