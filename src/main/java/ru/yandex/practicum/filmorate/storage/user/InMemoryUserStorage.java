package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public User createUser(User user) {
        Validator.validationCheck(user);
        idCounter++;
        user.setId(idCounter);
        users.put(idCounter, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        Validator.validationCheck(user);
        long filmId = user.getId();
        if (users.containsKey(filmId)) {
            users.put(filmId, user);
            return user;
        }
        throw new ValidationException("Такого idCounter не существует");
    }

    @Override
    public User findUserById(long id) {
        return users.get(id);
    }

    @Override
    public void addFriend(long id, long friendId) {
        checkUsersExisting(id, friendId);
        User user = users.get(id);
        User secondUser = users.get(friendId);
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
        secondUser.setFriends(secondUserFriends);
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        checkUsersExisting(id, friendId);
        User user = users.get(id);
        User secondUser = users.get(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> secondUserFriends = secondUser.getFriends();
        if (userFriends != null && userFriends.contains(friendId)) {
            userFriends.remove(friendId);
            user.setFriends(userFriends);
            secondUserFriends.remove(id);
            secondUser.setFriends(secondUserFriends);
            return;
        }
        throw new UserNotFoundException("Пользователи не являются друзьями");
    }

    @Override
    public List<User> getFriendsList(long id) {
        User user = users.get(id);
        Set<Long> userFriends = user.getFriends();
        if (userFriends == null || userFriends.isEmpty()) {
            throw new UserNotFoundException("Список друзей пуст");
        }
        List<User> friendsList = new ArrayList<>();
        for (Long friendId : userFriends) {
            friendsList.add(users.get(friendId));
        }
        return friendsList;
    }

    public List<User> getCommonFriends(long id, long otherId) {
        checkUsersExisting(id, otherId);
        User user = users.get(id);
        User secondUser = users.get(otherId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> secondUserFriends = secondUser.getFriends();
        List<User> commonFriends = new ArrayList<>();
        if (secondUserFriends == null || userFriends == null) {
            return commonFriends;
        }
        for (Long userId : userFriends) {
            if (secondUserFriends.contains(userId)) {
                commonFriends.add(users.get(userId));
            }
        }
        return commonFriends;
    }

    @Override
    public void deleteUsers() {
        users.clear();
    }

    private void checkUsersExisting(long id, long secondId) {
        if (!users.containsKey(id) || !users.containsKey(secondId)) {
            throw new UserNotFoundException("Такого пользователя / пользователей - не существует ");
        }
    }
}
